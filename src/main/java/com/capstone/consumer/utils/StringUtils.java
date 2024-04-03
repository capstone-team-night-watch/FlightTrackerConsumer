package com.capstone.consumer.utils;

import com.google.common.base.CaseFormat;

public class StringUtils {
    private StringUtils() {
    }

    public static String getMessageName(Class<?> clazz) {
        var messageName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, clazz.getSimpleName());
        return messageName.replace("-message", "");
    }
}
