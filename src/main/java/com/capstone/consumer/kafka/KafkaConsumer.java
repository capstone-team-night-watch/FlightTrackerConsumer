package com.capstone.consumer.kafka;

import com.capstone.shared.JsonHelper;
import com.capstone.consumer.servicehandler.LiveTrackingService;
import com.capstone.consumer.servicehandler.NoFlyZoneService;
import com.capstone.shared.bindings.CircularNoFlyZone;
import com.capstone.shared.bindings.FlightInformationKafkaDto;
import com.capstone.shared.bindings.PolygonNoFlyZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.stereotype.Component;

/**
 * This is the class where the kafka consumption gets set up.
 */
@Slf4j
@Component
public class KafkaConsumer {
    private static final String CONSUMER_ID = "flight-tracker-consumer";

    private final NoFlyZoneService noFlyZoneService;

    private final LiveTrackingService collisionListenerService;

    public KafkaConsumer(
            NoFlyZoneService noFlyZoneService,
            LiveTrackingService collisionListenerService
    ) {
        this.noFlyZoneService = noFlyZoneService;
        this.collisionListenerService = collisionListenerService;
    }

    /**
     * Handles a circular no-fly-zone message from Kafka.
     * @param message the message from Kafka
     */
    @KafkaListener(groupId = CONSUMER_ID, topicPartitions =
                        { @TopicPartition(topic = "CircularNoFlyZone",
                            partitions = "0",
                            partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0"))
                        })
    public void handleCircularNoFlyZone(String message) {
        log.atInfo().log("Received message from Kafka.");
        var optionalNoFlyZone = JsonHelper.fromJson(message, CircularNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse circular no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();

        noFlyZoneService.storeNoFlyZone(noFlyZone);
        collisionListenerService.trackNewNoFlyZone(noFlyZone);
    }


    /**
     * Handles a polygon no-fly-zone message from Kafka.
     * @param message the message from Kafka
     */
    @KafkaListener(groupId = CONSUMER_ID, topicPartitions =
                        { @TopicPartition(topic = "PolygonNoFlyZone",
                            partitions = "0",
                            partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0"))
                        })
    public void handlePolygonNoFlyZone(String message) {
        log.atInfo().log("Received message from Kafka.");
        var optionalNoFlyZone = JsonHelper.fromJson(message, PolygonNoFlyZone.class);

        if (optionalNoFlyZone.isEmpty()) {
            log.error("Failed to parse polygon no-fly-zone from Kafka message: {}", message);
            return;
        }

        var noFlyZone = optionalNoFlyZone.get();


        noFlyZoneService.storeNoFlyZone(noFlyZone);
        collisionListenerService.trackNewNoFlyZone(noFlyZone);
    }

    /**
     * Handles a flight update message from Kafka.
     * @param message the message from Kafka
     */
    @KafkaListener(groupId = CONSUMER_ID, topicPartitions =
                        { @TopicPartition(topic = "FlightLocationData",
                            partitions = "0",
                            partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0"))
                        })
    public void handleFlightUpdate(String message) {
        log.atInfo().log("Received message from Kafka.");
        var optionalFlightInformation = JsonHelper.fromJson(message, FlightInformationKafkaDto.class);

        if (optionalFlightInformation.isEmpty()) {
            log.error("Failed to parse flight information from Kafka message: {}", message);
            return;
        }

        var flightInformation = optionalFlightInformation.get();

        collisionListenerService.upsertFlightInformation(flightInformation);
    }
}
