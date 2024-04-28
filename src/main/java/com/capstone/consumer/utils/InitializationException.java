package com.capstone.consumer.utils;

public class InitializationException extends RuntimeException {
    public InitializationException(String message, Exception exception) {
        super(message, exception);
    }
}
