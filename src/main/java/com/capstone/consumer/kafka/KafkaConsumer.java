package com.capstone.consumer.kafka;

import com.capstone.shared.JsonHelper;
import com.capstone.consumer.servicehandler.LiveTrackingService;
import com.capstone.consumer.servicehandler.FlightLocationService;
import com.capstone.consumer.servicehandler.NoFlyZoneService;
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

    private final NoFlyZoneService noFlyZoneService;

    private final FlightLocationService flightLocationService;

    private final LiveTrackingService collisionListenerService;

    public KafkaConsumer(
            NoFlyZoneService noFlyZoneService,
            FlightLocationService flightLocationService,
            LiveTrackingService collisionListenerService
    ) {
        this.noFlyZoneService = noFlyZoneService;
        this.flightLocationService = flightLocationService;
        this.collisionListenerService = collisionListenerService;
    }

    @KafkaListener(topics = "CircularNoFlyZone")
    public void handleCircularNoFlyZone(String message) {
        var optionalNoFlyZone = JsonHelper.fromJson(message, CircularNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse circular no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();

        noFlyZoneService.storeNoFlyZone(noFlyZone);
        collisionListenerService.trackNewNoFlyZone(noFlyZone);
    }

    @KafkaListener(topics = "PolygonNoFlyZone")
    public void handlePolygonNoFlyZone(String message) {
        var optionalNoFlyZone = JsonHelper.fromJson(message, PolygonNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse polygon no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();


        noFlyZoneService.storeNoFlyZone(noFlyZone);
        collisionListenerService.trackNewNoFlyZone(noFlyZone);
    }

    @KafkaListener(topics = "FlightLocationData")
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
