package com.capstone.consumer.kafka;

import com.capstone.consumer.bindings.*;
import com.capstone.consumer.config.SocketIoService;
import com.capstone.consumer.servicehandler.FlightLocationService;
import com.capstone.consumer.servicehandler.NoFlyZoneService;
import com.capstone.consumer.shared.JsonHelper;
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

    public KafkaConsumer(
            SocketIoService socketIoService,
            NoFlyZoneService noFlyZoneService,
            FlightLocationService flightLocationService
    ) {
        this.socketIoService = socketIoService;
        this.noFlyZoneService = noFlyZoneService;
        this.flightLocationService = flightLocationService;
    }

    @KafkaListener(groupId = CONSUMER_ID, topics = "CircularNoFlyZone")
    public void handleCircularNoFlyZone(String message) {
        var optionalNoFlyZone = JsonHelper.fromJson(message, CircularNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse circular no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();

        noFlyZoneService.createNoFlyZone(noFlyZone);
        socketIoService.notifyNoFlyZoneCreated(noFlyZone);
    }

    @KafkaListener(groupId = CONSUMER_ID, topics = "PolygonNoFlyZone")
    public void handlePolygonNoFlyZone(String message) {
        var optionalNoFlyZone = JsonHelper.fromJson(message, PolygonNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse polygon no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();

        noFlyZoneService.createNoFlyZone(noFlyZone);
        socketIoService.notifyNoFlyZoneCreated(noFlyZone);
    }

    @KafkaListener(groupId = CONSUMER_ID, topics = "FlightLocationData")
    public void handleFlightUpdate(String message) {
        var optionalFlightInformation = JsonHelper.fromJson(message, FlightInformation.class);

        if (optionalFlightInformation.isEmpty()) {
            log.error("Failed to parse flight information from Kafka message: {}", message);
            return;
        }

        var flightInformation = optionalFlightInformation.get();

        socketIoService.notifyFlightLocationUpdated(flightInformation);
        flightLocationService.upsertFlightInformation(flightInformation);
    }
}
