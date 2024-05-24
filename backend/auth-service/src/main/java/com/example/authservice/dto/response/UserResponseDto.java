package com.example.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UserResponseDto {
    HttpStatus status;
    String message;
    UserDto data;
}
