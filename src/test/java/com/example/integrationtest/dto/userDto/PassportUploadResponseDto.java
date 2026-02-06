package com.example.integrationtest.dto.userDto;


import com.example.integrationtest.enumerated.VerificationStatus;

public record PassportUploadResponseDto(
        Long userId,
        String passportFrontPath,
        String passportBackPath,
        VerificationStatus passportStatus
) {
}

