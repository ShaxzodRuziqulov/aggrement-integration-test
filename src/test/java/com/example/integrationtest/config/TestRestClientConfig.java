package com.example.integrationtest.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class TestRestClientConfig {

    @Bean
    public RestClient.Builder testRestClientBuilder() {
        return RestClient.builder().defaultHeader( "X-pin", "");
    }
}
