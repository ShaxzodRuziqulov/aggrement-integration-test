package com.example.integrationtest.test.asset;

import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.assetDto.AssetCreateDto;
import com.example.integrationtest.dto.assetDto.AssetResponseDto;
import com.example.integrationtest.enumerated.AssetType;
import com.example.integrationtest.test.ServiceDtoRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class AssetFlowHelper {

    ServiceDtoRequest serviceDtoRequest = new ServiceDtoRequest();

    public void getAssets(ApiTestClient api, AssetResponseDto created) {
        AssetResponseDto fetched = api.get("/api/v1/assets/" + created.getId(), AssetResponseDto.class);
        assertThat(fetched.getId()).isEqualTo(created.getId());
    }

    public void getMyAssets(ApiTestClient api, AssetResponseDto created) {
        List<AssetResponseDto> myAssets = api.get(
                "/api/v1/assets/my",
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(myAssets).isNotNull();
        assertThat(myAssets.stream().anyMatch(a -> created.getId().equals(a.getId()))).isTrue();
    }

    public void updatedAsserts(ApiTestClient api, AssetResponseDto created) {
        AssetCreateDto updateDto = AssetCreateDto.builder()
                .type(created.getType())
                .build();
        updateDto.setDescription("Updated bike");
        updateDto.setUsageRules("Helmet required");
        updateDto.setDamageLiability("Owner liability");

        AssetResponseDto updated = api.put(
                "/api/v1/assets/" + created.getId(),
                updateDto,
                AssetResponseDto.class
        );

        assertThat(updated.getDescription()).isEqualTo("Updated bike");
        assertThat(updated.getUsageRules()).isEqualTo("Helmet required");
    }

    public AssetResponseDto getAssetResponseDto(ApiTestClient api) {
        AssetCreateDto dto = serviceDtoRequest.assetCreateDto();
        AssetResponseDto created = api.post("/api/v1/assets", dto, AssetResponseDto.class);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getType()).isEqualTo(AssetType.BIKE);
        assertThat(created.getDescription()).isEqualTo("Test bike");
        return created;
    }
}
