package com.example.integrationtest.dto.userDto;

import com.example.integrationtest.enumerated.UserBlockType;
import com.example.integrationtest.enumerated.VerificationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private UserBlockType blockType;
    private VerificationStatus passportStatus;
    private VerificationStatus pinflStatus;
}

