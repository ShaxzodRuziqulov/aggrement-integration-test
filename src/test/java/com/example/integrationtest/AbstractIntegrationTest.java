package com.example.integrationtest;

import com.example.integrationtest.config.TestAppConfig;
import com.example.integrationtest.config.TestRestClientConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.atomic.AtomicInteger;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestAppConfig.class})
@Import(TestRestClientConfig.class)
@ActiveProfiles("test")
public class AbstractIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    private static final AtomicInteger PHONE_SEQ =
            new AtomicInteger((int) (System.currentTimeMillis() % 1_000_000));

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    protected static String nextPhoneDigits() {
        int seq = PHONE_SEQ.getAndIncrement();
        return String.format("99%07d", Math.floorMod(seq, 10_000_000));
    }

    public static String nextPhone() {
        return "998" + nextPhoneDigits();
    }
}
