package com.capstone.consumer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * This is the class where the kafka consumption gets set up.
 */
@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(KafkaConsumer.class));

    /**
     * Template provided by spring to facilitate sending messages to a receiving source. This one is used to send to our web socket endpoint
     */
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Kafka listener method. Any message sent on the kafka broker is received here and then can be processed
     *
     * @param message The message being received in Kafka. It is assumed that this message contains flight data
     */
    @KafkaListener(id = "kafkaConsumer777", topics = "FlightData")
    public void flightDataListen(String message) {
        LOGGER.info("Received message from Kafka: {} ", message);

        sendFlightMessage(message);
    }

    /**
     * Kafka listener method. Any message sent on the kafka broker is received here and then can be processed
     *
     * @param message The message being received in Kafka. It is assumed that this message contains NOTAM TFR
     */
    @KafkaListener(id = "kafkaTFRConsumer777", topics = "TFRData")
    public void TfrListen(String message) {
        LOGGER.info("Received message from Kafka: {} ", message);

        processTFRMessage(message);
    }

    /**
     * Simple method to send a message to the connected web socket endpoint.
     *
     * @param message Message containing flight information that has been received from the Kafka broker
     */
    public void sendFlightMessage(String message) {
        LOGGER.info("Sending message to web socket endpoint: /topic/liveCoords");

        messagingTemplate.convertAndSend("/topic/liveCoords", message);
    }

    public void processTFRMessage(String message) {
        LOGGER.info("Received TFR to process");
        //
        // Save to DB
        // TODO: Send message
    }
}
