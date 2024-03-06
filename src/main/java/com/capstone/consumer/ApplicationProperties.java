package com.capstone.consumer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationProperties {
    @Value("${server.port}")
    private String serverPort;

    @Value("${kafka.host}")
    private String kafkaHostUrl;

    @Value("${socketio.port}")
    private int socketIoPort;
}
