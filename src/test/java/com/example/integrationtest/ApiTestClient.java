package com.example.integrationtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class ApiTestClient {

    private final RestClient restClient;

    public ApiTestClient(@Autowired RestClient restClient) {
        this.restClient = restClient;
    }

    public ApiTestClient withBearer(String token) {
        RestClient authed = restClient.mutate()
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
        return new ApiTestClient(authed);
    }

    public <T> T get(String url, Class<T> type) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(type);
    }

    public <T> T get(String url, ParameterizedTypeReference<T> type) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(type);
    }

    public <T> T post(String url, Object dto, Class<T> type) {
        return restClient.post()
                .uri(url)
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(type);
    }

    public <T> T post(String url, Object dto, ParameterizedTypeReference<T> type) {
        return restClient.post().uri(url)
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(type);
    }

    public <T> T put(String url, Object body, Class<T> type) {
        var req = restClient.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON);

        if (body != null) {
            req = req.body(body);
        }

        return req.retrieve().body(type);
    }

    public <T> T put(String url, Object body, ParameterizedTypeReference<T> type) {
        var req = restClient.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON);
        if (body != null) {
            req = req.body(body);
        }
        return req.retrieve().body(type);
    }
}
