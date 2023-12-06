package com.github.pawelszumny.socialnetwork.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestSerializer {
    public static <T> String serialize(T object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing request object to JSON", e);
        }
    }
}
