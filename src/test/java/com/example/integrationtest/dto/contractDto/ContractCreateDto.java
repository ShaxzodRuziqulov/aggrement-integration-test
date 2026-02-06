package com.example.integrationtest.dto.contractDto;

import com.example.integrationtest.enumerated.BillingUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ContractCreateDto {

    private Long assetId;

    private BigDecimal billingAmount;
    private BillingUnit billingUnit;
    private Integer prepaidPeriods;

    private String language;
    private Long renterId;
}

