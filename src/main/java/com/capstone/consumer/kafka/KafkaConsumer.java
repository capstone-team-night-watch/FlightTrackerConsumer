package com.capstone.consumer.kafka;

import com.capstone.consumer.config.SocketIoService;
import com.capstone.consumer.servicehandler.CollisionListenerService;
import com.capstone.consumer.servicehandler.FlightLocationService;
import com.capstone.consumer.servicehandler.NoFlyZoneService;
import com.capstone.shared.JsonHelper;
import com.capstone.shared.bindings.CircularNoFlyZone;
import com.capstone.shared.bindings.FlightInformationKafkaDto;
import com.capstone.shared.bindings.PolygonNoFlyZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * This is the class where the kafka consumption gets set up.
 */
@Slf4j
@Component
public class KafkaConsumer {
    private static final String CONSUMER_ID = "flight-tracker-consumer";
    private final SocketIoService socketIoService;

    /**
     * Template provided by spring to facilitate sending messages to a receiving source. This one is used to send to our web socket endpoint
     */

    private final NoFlyZoneService noFlyZoneService;

    private final FlightLocationService flightLocationService;

    private final CollisionListenerService collisionListenerService;

    public KafkaConsumer(
            SocketIoService socketIoService,
            NoFlyZoneService noFlyZoneService,
            FlightLocationService flightLocationService,
            CollisionListenerService collisionListenerService
    ) {
        this.socketIoService = socketIoService;
        this.noFlyZoneService = noFlyZoneService;
        this.flightLocationService = flightLocationService;
        this.collisionListenerService = collisionListenerService;
    }

    @KafkaListener(groupId = CONSUMER_ID, topics = "CircularNoFlyZone")
    public void handleCircularNoFlyZone(String message) {
        var optionalNoFlyZone = JsonHelper.fromJson(message, CircularNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse circular no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();

        noFlyZoneService.storeNoFlyZone(noFlyZone);
        socketIoService.notifyNoFlyZoneCreated(noFlyZone);
        collisionListenerService.trackNewNoFlyZone(noFlyZone);
    }

    @KafkaListener(groupId = CONSUMER_ID, topics = "PolygonNoFlyZone")
    public void handlePolygonNoFlyZone(String message) {
        var optionalNoFlyZone = JsonHelper.fromJson(message, PolygonNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse polygon no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();


        noFlyZoneService.storeNoFlyZone(noFlyZone);
        socketIoService.notifyNoFlyZoneCreated(noFlyZone);
        collisionListenerService.trackNewNoFlyZone(noFlyZone);
    }

    @KafkaListener(groupId = CONSUMER_ID, topics = "FlightLocationData")
    public void handleFlightUpdate(String message) {
        var optionalFlightInformation = JsonHelper.fromJson(message, FlightInformationKafkaDto.class);

        if (optionalFlightInformation.isEmpty()) {
            log.error("Failed to parse flight information from Kafka message: {}", message);
            return;
        }

        var flightInformation = optionalFlightInformation.get();

        flightLocationService.upsertFlightInformation(flightInformation);

        collisionListenerService.upsertFlightInformation(flightInformation);
    }
}
