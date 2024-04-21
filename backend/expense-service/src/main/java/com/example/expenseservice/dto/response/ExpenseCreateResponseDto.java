package com.example.expenseservice.dto.response;

import lombok.Builder;

@Builder
public class ExpenseCreateResponseDto {
    private Long id;
    private Long accountId;
    private String userId;
    private String category;
    private String description;
    private Double amount;
    private String date;
}
