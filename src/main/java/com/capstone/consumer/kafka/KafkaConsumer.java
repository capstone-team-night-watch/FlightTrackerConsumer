package com.capstone.consumer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class KafkaConsumer {
    Logger logger = Logger.getLogger(String.valueOf(KafkaConsumer.class));

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(id = "kafkaConsumerExampleId3", topics="FlightData")
    public void listen(String flightData) {
        //displayMessageClient.displayMessage(flightData);
        logger.info("Received flightData: " + flightData);

        sendMessage(flightData);
    }

    public void sendMessage(String message) {
        messagingTemplate.convertAndSend("/topic/liveCoords", message);
    }
}
