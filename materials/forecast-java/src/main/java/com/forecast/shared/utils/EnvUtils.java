package com.forecast.shared.utils;

public class EnvUtils {
    public static String getEnv(String key, String fallback) {
        String value = System.getenv(key);
        return value != null ? value : fallback;
    }
}
