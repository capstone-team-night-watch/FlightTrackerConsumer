package com.capstone.consumer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaConsumer {
    Logger logger = Logger.getLogger(String.valueOf(KafkaConsumer.class));
    @KafkaListener(id = "kafkaConsumerExampleId", topics="FlightData")
    @SendTo("/topic/liveCoords")
    public String listen(String flightData) {
        //displayMessageClient.displayMessage(flightData);
        logger.info("Received flightData: " + flightData);

        return flightData;
    }
}
