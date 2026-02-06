package com.example.integrationtest.dto.paymentDto;

import com.example.integrationtest.enumerated.PaymentProvider;
import com.example.integrationtest.enumerated.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCallbackRequest {

    private Long paymentId;
    private PaymentProvider provider;
    private String providerTxnId;
    private PaymentStatus status;
    private BigDecimal amount;
}
