package com.capstone.shared;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Helper class for JSON serialization and deserialization
 */
@Slf4j
public class JsonHelper {
    private static final Validator validator;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        var factory = Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonHelper() {
    }

    /**
     * Serializes an object to JSON
     *
     * @param obj object to serialized
     * @return JSON string of the object
     */
    public static Optional<String> toJson(Object obj) {
        try {
            var value = objectMapper.writeValueAsString(obj);
            return Optional.of(value);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Deserializes a JSON string to an object
     *
     * @param json  JSON string to deserialize
     * @param clazz class of the object to deserialize to
     * @param <T>   type of the object to deserialize to
     * @return Optional of the deserialized object
     */
    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        try {
            var value = objectMapper.readValue(json, clazz);

            var constraintViolations = validator.validate(value);

            if (!constraintViolations.isEmpty()) {
                log.error("Validation failed for object: {} because of following constraint violations {}", value, constraintViolations);
                return Optional.empty();
            }

            return Optional.of(value);
        } catch (Exception exception) {
            log.error("Error deserializing object", exception);
            return Optional.empty();
        }
    }
}


