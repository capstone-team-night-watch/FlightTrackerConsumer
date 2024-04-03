package com.capstone.consumer.kafka;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class KafkaConsumerConfigTest {

    private KafkaConsumerConfig kafkaConsumerConfig;

    @Before
    public void setUp() {
        this.kafkaConsumerConfig = new KafkaConsumerConfig();
    }

    @Test
    public void consumerFactoryShouldReturnNonNull() {
        String KAFKA_URL = "localhost:test";

        ConsumerFactory<Long, String> consumerFactory = kafkaConsumerConfig.consumerFactory(KAFKA_URL);

        assertNotNull(consumerFactory);
    }

    @Test
    public void kafkaListenerContainerFactoryShouldReturnValid() {
        String KAFKA_URL = "localhost:test";

        ConcurrentKafkaListenerContainerFactory<Long, String> factory = kafkaConsumerConfig.kafkaListenerContainerFactory(KAFKA_URL);

        assertNotNull(factory);
    }
}