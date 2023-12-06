package com.github.pawelszumny.socialnetwork.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pawelszumny.socialnetwork.User;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;

public class UserDeserializer {
    public static List<User> deserializeUsers(Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String responseBodyString = response.getBody().asString();
            return objectMapper.readValue(responseBodyString, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing user list", e);
        }
    }
}
