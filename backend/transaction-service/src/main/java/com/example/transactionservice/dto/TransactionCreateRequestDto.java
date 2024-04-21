package com.example.transactionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionCreateRequestDto {
    @NotNull(message = "fromAccountId is required")
    private Long fromAccountId;

    @NotNull(message = "toAccountId is required")
    private Long toAccountId;

    @NotNull(message = "CategoryId is required")
    private Long categoryId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    private String description;
}
