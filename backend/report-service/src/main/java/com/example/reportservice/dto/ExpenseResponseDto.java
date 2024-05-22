package com.example.reportservice.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ExpenseResponseDto {
    private HttpStatus status;
    private String message;
    private List<ExpenseDto> data;
}
