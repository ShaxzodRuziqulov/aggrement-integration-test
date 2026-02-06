package com.example.integrationtest.dto.paymentDto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashPaymentRequest {

    private BigDecimal amount;
    private String note;
}

