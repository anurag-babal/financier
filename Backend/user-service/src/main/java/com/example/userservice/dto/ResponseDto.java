package com.example.userservice.dto;

import lombok.Builder;

@Builder
public class ResponseDto {
    int status;
    String message;
    Object data;
}
