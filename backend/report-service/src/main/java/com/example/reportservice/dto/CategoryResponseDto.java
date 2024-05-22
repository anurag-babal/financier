package com.example.reportservice.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class CategoryResponseDto {
    private HttpStatus status;
    private String message;
    private List<CategoryDto> data;
}
