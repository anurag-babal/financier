package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserCreateResponseDto {
    private int id;
    private String loginId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String dateOfBirth;
    private Double budget;
}
