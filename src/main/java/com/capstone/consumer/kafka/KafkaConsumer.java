package com.capstone.consumer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * This is the class where the kafka consumption gets set up.
 */
@Component
public class KafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(KafkaConsumer.class));

    /**
     * Template provided by spring to facilitate sending messages to a receiving source. This one is used to send to our web socket endpoint
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Kafka listener method. Any message sent on the kafka broker is received here and then can be processed
     * @param message The message being received in Kafka. It is assumed that this message contains flight data
     */
    @KafkaListener(id = "kafkaConsumerExampleId7", topics="FlightData")
    public void listen(String message) {
        LOGGER.info("Received message from Kafka: " + message);

        sendMessage(message);
    }

    /**
     * Simple method to send a message to the connected web socket endpoint.
     * @param message Message containing flight information that has been received from the Kafka broker
     */
    public void sendMessage(String message) {
        LOGGER.info("Sending message to web socket endpoint: /topic/liveCoords");

        messagingTemplate.convertAndSend("/topic/liveCoords", message);
    }
}
