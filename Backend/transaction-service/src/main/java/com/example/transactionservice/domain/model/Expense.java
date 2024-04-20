package com.example.transactionservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Expense {
    private Long id;
    private Long accountId;
    private String category;
    private String description;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
}
