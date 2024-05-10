package com.capstone.consumer.utils;

import com.google.common.base.CaseFormat;

/**
 * Utility class for string operations
 */
public class StringUtils {
    private StringUtils() {
    }

    /**
     * Converts a class name to a message name
     *
     * @param clazz The class to convert
     * @return The message name
     */
    public static String getMessageName(Class<?> clazz) {
        var messageName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, clazz.getSimpleName());
        return messageName.replace("-message", "");
    }
}
