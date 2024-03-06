package com.capstone.consumer.kafka;

import com.capstone.consumer.bindings.CircularNoFlyZone;
import com.capstone.consumer.bindings.GeographicCoordinates2D;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.config.SocketIoService;
import com.capstone.consumer.shared.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.capstone.consumer.beans.TfrBean;
import com.capstone.consumer.bindings.TfrNotam;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the class where the kafka consumption gets set up.
 */
@Slf4j
@Component
public class KafkaConsumer {
    private final SocketIoService socketIoService;

    /**
     * Template provided by spring to facilitate sending messages to a receiving source. This one is used to send to our web socket endpoint
     */
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumer(SimpMessagingTemplate messagingTemplate, SocketIoService socketIoService) {
        this.socketIoService = socketIoService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Kafka listener method. Any message sent on the kafka broker is received here and then can be processed
     *
     * @param message The message being received in Kafka. It is assumed that this message contains flight data
     */
    @KafkaListener(id = "flight-tracker-consumer", topics = "FlightData")
    public void flightDataListen(String message) {
        log.info("Received message from Kafka: {} ", message);

        sendFlightMessage(message);
    }

    /**
     * Kafka listener method. Any message sent on the kafka broker is received here and then can be processed
     *
     * @param message The message being received in Kafka. It is assumed that this message contains NOTAM TFR
     */
    @KafkaListener(id = "kafkaTFRConsumer777", topics = "TFRData")
    public void handleTfrData(String message) {
        log.info("Received message from Kafka: {} ", message);

        var notam = JsonHelper.fromJson(message, TfrNotam.class);

        if (notam.isEmpty()) {
            return;
        }


        handleNewNoFlyZone(notam.get());
    }

    /**
     * Simple method to send a message to the connected web socket endpoint.
     *
     * @param message Message containing flight information that has been received from the Kafka broker
     */
    public void sendFlightMessage(String message) {
        log.info("Sending message to web socket endpoint: /topic/liveCoords");

        messagingTemplate.convertAndSend("/topic/liveCoords", message);
    }

    public void handleNewNoFlyZone(TfrNotam notam) {
        TfrBean.addNewTFR(notam);

        messagingTemplate.convertAndSend("/notam/newTfr", JsonHelper.toJson(notam));

        // Handle circular no-fly-zone
        if (notam.getNotamType().equals("CIRCLE")) {
            var newNoFlyZone = new CircularNoFlyZone()
                    .setCenter(new GeographicCoordinates2D(notam.getLatlong().get(0), notam.getLatlong().get(1)))
                    .setRadius(notam.getRadius())
                    .setNotamNumber(notam.getNotamNumber())
                    .setAltitude(10000);

            socketIoService.notifyNoFlyZoneCreated(newNoFlyZone);
        }

        // Handle polygon
        else if (notam.getNotamType().equals("Polygon")) {
            var newNoFlyZone = new PolygonNoFlyZone()
                    .setNotamNumber(notam.getNotamNumber())
                    .setAltitude(10000);

            socketIoService.notifyNoFlyZoneCreated(newNoFlyZone);
        }
    }
}
