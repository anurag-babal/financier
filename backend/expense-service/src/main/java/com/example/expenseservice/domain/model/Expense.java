package com.example.expenseservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Expense {
    private Long id;
    private String userId;
    private String category;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
}
