package com.capstone.consumer.bindings;

import lombok.Data;

@Data
public class HealthCheckResponse {
    private String status;

    private String kafkaUrl;

    private String serverPort;
}
