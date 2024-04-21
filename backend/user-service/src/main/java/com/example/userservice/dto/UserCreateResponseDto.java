package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

//@Builder
@Data
public class UserCreateResponseDto {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String dateOfBirth;
    private String message;
}
