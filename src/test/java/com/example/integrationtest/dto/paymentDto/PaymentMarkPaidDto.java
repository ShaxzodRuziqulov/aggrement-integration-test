package com.example.integrationtest.dto.paymentDto;

import com.example.integrationtest.enumerated.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMarkPaidDto {

    private PaymentMethod method;
}
