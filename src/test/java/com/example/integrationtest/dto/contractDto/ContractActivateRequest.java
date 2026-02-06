package com.example.integrationtest.dto.contractDto;

import com.example.integrationtest.enumerated.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContractActivateRequest {

    private PaymentMethod prepaidMethod = PaymentMethod.CASH;
    private BigDecimal prepaidAmount;
    private String handoverNote;
    private LocalDate periodStart;
}