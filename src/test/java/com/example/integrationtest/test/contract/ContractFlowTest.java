package com.example.integrationtest.test.contract;

import com.example.integrationtest.AbstractIntegrationTest;
import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.assetDto.AssetResponseDto;
import com.example.integrationtest.dto.auth.AuthResponseDto;
import com.example.integrationtest.dto.auth.MessageResponse;
import com.example.integrationtest.dto.contractDto.ContractResponseDto;
import com.example.integrationtest.test.ServiceDtoRequest;
import com.example.integrationtest.test.ServiceRestServiceFactory;
import com.example.integrationtest.test.asset.AssetFlowHelper;
import com.example.integrationtest.test.auth.RegistrationFlowHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ContractFlowTest extends AbstractIntegrationTest {

    @Autowired
    private ContractFlowHelper helper;
    @Autowired
    private ServiceRestServiceFactory factory;
    @Autowired
    private RegistrationFlowHelper authHelper;
    @Autowired
    private AssetFlowHelper assetHelper;

    ApiTestClient apiClient;
    ServiceDtoRequest request = new ServiceDtoRequest();

    @BeforeEach
    void setUp() {
        factory.createApiClient("agreement");
    }

    private AuthResponseDto registerUser(ApiTestClient api) {
        String phone = nextPhone();
        MessageResponse otp = authHelper.requestRegisterOtp(api, phone);
        assertThat(otp.getOtp()).isNotBlank();
        return authHelper.verifyRegisterOtp(api, phone, otp.getOtp(), "John", "Doe");
    }

    @Test
    void contractFlow() {
        ApiTestClient api = apiClient.withBearer(factory.registerAndGetToken());

        AuthResponseDto owner = registerUser(api);
        AuthResponseDto renter = registerUser(api);

        ApiTestClient ownerApi = apiClient.withBearer(owner.getToken());
        ApiTestClient renterApi = apiClient.withBearer(owner.getToken());

        AssetResponseDto asset = assetHelper.getAssetResponseDto(ownerApi, request.assetCreateDto());
        ContractResponseDto contract = helper.createContract(ownerApi, request.contractCreateDto(asset.getId(), renter.getId()));

    }
}
