package com.example.integrationtest.test.contract;

import com.example.integrationtest.ApiTestClient;
import com.example.integrationtest.dto.contractDto.ContractCreateDto;
import com.example.integrationtest.dto.contractDto.ContractResponseDto;
import com.example.integrationtest.dto.userDto.PinflSubmitDto;
import com.example.integrationtest.dto.userDto.UserDto;
import com.example.integrationtest.enumerated.ContractStatus;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ContractFlowHelper {

    public ContractResponseDto createContract(ApiTestClient api, ContractCreateDto createDto) {
        ContractResponseDto created = api.post("/api/v1/contracts", createDto, ContractResponseDto.class);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getStatus()).isEqualTo(ContractStatus.DRAFT);

        return created;
    }

    public void sendToRenter(ApiTestClient api, Long contractId) {
        api.post("/api/v1/contracts/" + contractId + "/send-to-renter", null, Void.class);
    }

    public void renterSubmit(ApiTestClient api, Long contractId, PinflSubmitDto request) {
        api.post("/api/v1/contracts/" + contractId + "/renter-submit", request, Void.class);
    }

    public ContractResponseDto get(ApiTestClient api, Long contractId) {
        return api.get("/api/v1/contracts/" + contractId, ContractResponseDto.class);
    }

    public UserDto userId(ApiTestClient api, Long userId) {
        UserDto user = api.get("/api/v1/users/" + userId, UserDto.class);
    }
}
