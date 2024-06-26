package com.capstone.consumer.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Configuration class for the Kafka consumer that allows for more complex parsing and such. It is currently unused.
 * However, could be used instead of this application strictly being an event listener facilitated by the KafkaConsumer Class
 */
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<Long, String> consumerFactory(@Value("${kafka.host}") String kafkaUrl) {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        // Set the kafka group id to a random one so that all the content of the topic is re-consumed every time.
        // Update this once you are a persistent storage layer like a database.
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, String> kafkaListenerContainerFactory(@Value("${kafka.host}") String kafkaUrl){
        ConcurrentKafkaListenerContainerFactory<Long, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(kafkaUrl));
        return factory;
    }
}
