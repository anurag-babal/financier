package com.example.reportservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TransactionResponseDto {
    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private Long categoryId;
    private Double amount;
    private String timestamp;
    private String transactionType;
    private String transactionStatus;
    private String description;
}
