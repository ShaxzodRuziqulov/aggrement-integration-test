package com.example.integrationtest.test;

import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.auth.AuthResponseDto;
import com.example.integrationtest.dto.auth.MessageResponse;
import com.example.integrationtest.test.auth.RegistrationFlowHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.example.integrationtest.AbstractIntegrationTest.nextPhone;
import static org.assertj.core.api.Assertions.assertThat;

@Component
@RequiredArgsConstructor
public class ServiceRestServiceFactory {

    private final RestClient.Builder restBuilder;
    private final RegistrationFlowHelper authHelper;

    public RestClient create(String service) {
        return switch (service) {
            case "agreement" -> restBuilder
                    .baseUrl("http://localhost:8081")
                    .build();
            default -> throw new IllegalStateException("Unexpected value: " + service);
        };
    }

    public ApiTestClient createApiClient(String service) {
        return new ApiTestClient(create(service));
    }

    public String registerAndGetToken() {
        return registerAndGetToken("agreement");
    }

    public String registerAndGetToken(String service) {
        ApiTestClient api = createApiClient(service);
        String phone = nextPhone();
        MessageResponse otp = authHelper.requestRegisterOtp(api, phone);
        assertThat(otp.getOtp()).isNotBlank();

        AuthResponseDto auth = authHelper.verifyRegisterOtp(api, phone, otp.getOtp(), "John", "Doe");
        assertThat(auth.getToken()).isNotBlank();
        return auth.getToken();
    }
}
