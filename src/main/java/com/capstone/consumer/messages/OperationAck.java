package com.capstone.consumer.messages;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class OperationAck {
    private String message;

    private HttpStatus code;
}
