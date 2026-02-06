package com.example.integrationtest.test;

import com.example.integrationtest.AbstractIntegrationTest;
import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.assetDto.AssetCreateDto;
import com.example.integrationtest.dto.assetDto.AssetResponseDto;
import com.example.integrationtest.enumerated.AssetType;
import com.example.integrationtest.test.asset.AssetFlowHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AssetFlowTest extends AbstractIntegrationTest {

    @Autowired
    private AssetFlowHelper helper;

    @Autowired
    private ServiceRestServiceFactory factory;

    ApiTestClient apiClient;

    @BeforeEach
    void setUp() {
        factory.createApiClient("agreement");
    }

    @Test
    @DisplayName("User can create, view, list, and update own asset without 403")
    void assetCrudFlow() {
        ApiTestClient api = apiClient.withBearer(factory.registerAndGetToken());

        AssetResponseDto created = helper.getAssetResponseDto(api);

        helper.getMyAssets(api, created);

        helper.getAssets(api, created);

        helper.updatedAsserts(api, created);
    }
}
