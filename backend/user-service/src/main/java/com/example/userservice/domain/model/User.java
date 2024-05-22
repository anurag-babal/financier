package com.example.userservice.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    private String loginId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
}
