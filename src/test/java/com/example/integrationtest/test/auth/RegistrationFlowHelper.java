package com.example.integrationtest.test.auth;

import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.auth.*;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class RegistrationFlowHelper {

    // ============ LOGIN FLOW ============

    public MessageResponse requestLoginOtp(ApiTestClient api, String phoneNumber) {
        LoginRequestDto request = LoginRequestDto.builder()
                .phoneNumber(phoneNumber)
                .build();

        MessageResponse response = api.post("/api/v1/auth/login/request", request, MessageResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).contains("OTP sent successfully");

        return response;
    }

    public AuthResponseDto verifyLoginOtp(ApiTestClient api, String phoneNumber, String code) {
        VerifyOtpDto request = VerifyOtpDto.builder()
                .phoneNumber(phoneNumber)
                .code(code)
                .build();

        AuthResponseDto response = api.post("/api/v1/auth/login/verify", request, AuthResponseDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
        assertThat(response.getUserId()).isNotNull();
        assertThat(response.getMessage()).contains("Login successful");

        return response;
    }

    // ============ REGISTRATION FLOW ============

    public MessageResponse requestRegisterOtp(ApiTestClient api, String phoneNumber) {
        RegistrationRequestDto request = RegistrationRequestDto.builder()
                .phoneNumber(phoneNumber)
                .build();

        MessageResponse response = api.post("/api/v1/auth/register/request", request, MessageResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).contains("OTP sent successfully");

        return response;
    }

    public AuthResponseDto verifyRegisterOtp(ApiTestClient api, String phoneNumber, String code,
                                             String firstName, String lastName) {
        VerifyRegistrationDto request = VerifyRegistrationDto.builder()
                .phoneNumber(phoneNumber)
                .code(code)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        AuthResponseDto response = api.post("/api/v1/auth/register/verify", request, AuthResponseDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
        assertThat(response.getUserId()).isNotNull();
        assertThat(response.getFirstName()).isEqualTo(firstName);
        assertThat(response.getLastName()).isEqualTo(lastName);
        assertThat(response.getMessage()).contains("Registration successful");

        return response;
    }
}