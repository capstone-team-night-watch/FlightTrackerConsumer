package com.capstone.consumer.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
            log.error("Error deserializing object", exception);
            return Optional.empty();
        }
    }
}


