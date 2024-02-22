package com.capstone.consumer.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KafkaConsumerTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    public void shouldCallSimpMessagingTemplate(){
        kafkaConsumer.flightDataListen("FLIGHT DATA");

        verify(simpMessagingTemplate).convertAndSend(anyString(), anyString());
    }
}
