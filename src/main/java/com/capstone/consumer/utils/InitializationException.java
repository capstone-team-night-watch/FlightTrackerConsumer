package com.capstone.consumer.utils;

/**
 * Exception thrown when an error occurs during initialization
 */
public class InitializationException extends RuntimeException {
    /**
     * Constructor
     * @param message The error message
     */
    public InitializationException(String message, Exception exception) {
        super(message, exception);
    }
}
