package com.example.userservice.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ResponseDto {
    HttpStatus status;
    String message;
    Object data;
}
