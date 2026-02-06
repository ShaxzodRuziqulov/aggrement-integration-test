package com.example.integrationtest.dto.paymentDto;

import com.example.integrationtest.enumerated.PaymentProvider;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardInitRequest {

    private BigDecimal amount;
    private PaymentProvider provider;
    private String returnUrl;
    private String callbackUrl;
}
