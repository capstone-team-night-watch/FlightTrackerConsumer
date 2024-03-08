package com.capstone.consumer.shared;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Optional<String> toJson(Object obj) {
        try {
            var value = objectMapper.writeValueAsString(obj);
            return Optional.of(value);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        try {
            var value =  objectMapper.readValue(json, clazz);
            return Optional.of(value);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}


