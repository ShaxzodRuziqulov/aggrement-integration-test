package com.example.integrationtest.dto.paymentDto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ManualTransferCreateRequest {
    private BigDecimal amount;
    private String note;
    private String proofPath;
}
