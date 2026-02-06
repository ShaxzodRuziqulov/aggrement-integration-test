package com.example.integrationtest.test;

import org.springframework.web.client.RestClientResponseException;

public final class TestErrorUtils {
    private TestErrorUtils() {}

    public static String extractBodyLower(RestClientResponseException ex) {
        String body = ex.getResponseBodyAsString();
        if (body == null) {
            return "";
        }
        return body.toLowerCase();
    }
}

