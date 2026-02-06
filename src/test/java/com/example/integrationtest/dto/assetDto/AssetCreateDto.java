package com.example.integrationtest.dto.assetDto;

import com.example.integrationtest.enumerated.AssetType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetCreateDto {

    private AssetType type;
    private String description;
    private String usageRules;
    private String damageLiability;
}

