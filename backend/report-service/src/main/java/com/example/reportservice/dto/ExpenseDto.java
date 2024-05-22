package com.example.reportservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseDto {
    private Long id;
    private String userId;
    private String category;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
}
