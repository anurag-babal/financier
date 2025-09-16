package com.financier.userservice.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDtos {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = "Name cannot be blank")
        private String name;

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        private String email;
        
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password cannot be blank")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthResponse {
        private String token;
    }
}
