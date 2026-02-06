package com.example.integrationtest.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private Long userId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String role;
    private String message;
}