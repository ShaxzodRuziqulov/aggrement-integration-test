package com.example.integrationtest.test;

import com.example.integrationtest.dto.assetDto.AssetCreateDto;
import com.example.integrationtest.dto.contractDto.ContractCreateDto;
import com.example.integrationtest.enumerated.AssetType;
import com.example.integrationtest.enumerated.BillingUnit;

import java.math.BigDecimal;

public class ServiceDtoRequest {

    public AssetCreateDto assetCreateDto() {
        return AssetCreateDto.builder()
                .type(AssetType.BIKE)
                .description("Contract asset")
                .usageRules("No off road")
                .damageLiability("Owner liability")
                .build();
    }

    public ContractCreateDto contractCreateDto(Long assetId, Long renterId) {
        return ContractCreateDto.builder()
                .assetId(assetId)
                .billingAmount(BigDecimal.valueOf(100))
                .billingUnit(BillingUnit.WEEK)
                .prepaidPeriods(1)
                .language("Uzbekistan")
                .renterId(renterId)
                .build();
    }
}