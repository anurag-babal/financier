package com.example.transactionservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {
    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private Long referenceId;
    private Long categoryId;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
    private String description;
}
