package com.example.integrationtest.test;

import com.example.integrationtest.AbstractIntegrationTest;
import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.auth.AuthResponseDto;
import com.example.integrationtest.dto.auth.LoginRequestDto;
import com.example.integrationtest.dto.auth.MessageResponse;
import com.example.integrationtest.dto.auth.RegistrationRequestDto;
import com.example.integrationtest.test.auth.RegistrationFlowHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientResponseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class AuthenticationFlowTest extends AbstractIntegrationTest {

    @Autowired private RegistrationFlowHelper helper;
    @Autowired private ServiceRestServiceFactory factory;

    private ApiTestClient api;

    @BeforeEach
    void setUp() {
        api = new ApiTestClient(factory.create("agreement"));
    }


//    @Test
    @DisplayName("Complete Registration Flow - Request OTP and Verify")
    void completeRegistrationFlow() {
        String phone = nextPhone();
        String firstName = "John";
        String lastName = "Doe";

        MessageResponse otpResponse = helper.requestRegisterOtp(api, phone);
        assertThat(otpResponse.isSuccess()).isTrue();

        String otp = otpResponse.getOtp();
        assertThat(otp).as("OTP must be exposed in test env").isNotBlank();

        AuthResponseDto authResponse = helper.verifyRegisterOtp(api, phone, otp, firstName, lastName);

        assertThat(authResponse.getToken()).isNotBlank();
        assertThat(authResponse.getPhoneNumber()).contains(phone);
        assertThat(authResponse.getRole()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("Complete Login Flow - Request OTP and Verify")
    void completeLoginFlow() {
        // Arrange: create user first (no dependency on other tests)
        String phone = nextPhone();
        String firstName = "John";
        String lastName = "Doe";

        MessageResponse regOtp = helper.requestRegisterOtp(api, phone);
        assertThat(regOtp.getOtp()).isNotBlank();

        helper.verifyRegisterOtp(api, phone, regOtp.getOtp(), firstName, lastName);

//        // Act: login
        MessageResponse loginOtp = helper.requestLoginOtp(api, phone);
        assertThat(loginOtp.isSuccess()).isTrue();
        assertThat(loginOtp.getOtp()).isNotBlank();
//
//        AuthResponseDto authResponse = helper.verifyLoginOtp(api, phone, loginOtp.getOtp());
//
//        // Assert
//        assertThat(authResponse.getToken()).isNotBlank();
//        assertThat(authResponse.getFirstName()).isEqualTo(firstName);
//        assertThat(authResponse.getLastName()).isEqualTo(lastName);
    }

//    @Test
    @DisplayName("Registration with already existing phone number should fail")
    void registrationWithExistingPhoneFails() {
        // Arrange: user exists
        String phone = nextPhone();
        MessageResponse regOtp = helper.requestRegisterOtp(api, phone);
        assertThat(regOtp.getOtp()).isNotBlank();
        helper.verifyRegisterOtp(api, phone, regOtp.getOtp(), "John", "Doe");

        // Act: try request register again with same phone
        RegistrationRequestDto request = RegistrationRequestDto.builder()
                .phoneNumber(phone)
                .build();

        RestClientResponseException ex = catchThrowableOfType(
                () -> api.post("/api/v1/auth/register/request", request, MessageResponse.class),
                RestClientResponseException.class
        );

        // Assert
        assertThat(ex).isNotNull();
        assertThat(ex.getStatusCode().is4xxClientError()).isTrue();

        String message = TestErrorUtils.extractBodyLower(ex);
        assertThat(message).containsAnyOf("already", "exist", "registered");
    }

//    @Test
    @DisplayName("Login with non-existent phone number should fail")
    void loginWithNonExistentPhoneFails() {
        String nonExistentPhone = nextPhone();

        LoginRequestDto request = LoginRequestDto.builder()
                .phoneNumber(nonExistentPhone)
                .build();

        RestClientResponseException ex = catchThrowableOfType(
                () -> api.post("/api/v1/auth/login/request", request, MessageResponse.class),
                RestClientResponseException.class
        );

        assertThat(ex).isNotNull();
        assertThat(ex.getStatusCode().is4xxClientError()).isTrue();

        String message = TestErrorUtils.extractBodyLower(ex);
        assertThat(message).containsAnyOf("not found", "no user", "exist");
    }
}
