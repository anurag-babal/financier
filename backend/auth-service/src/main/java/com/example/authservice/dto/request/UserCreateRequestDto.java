package com.example.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserCreateRequestDto {
    @NotBlank(message = "Login ID is mandatory")
    private String loginId;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    private String phoneNumber;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "Date of birth should be in yyyy-MM-dd format")
    private String dateOfBirth;
    private Double budget;
}
