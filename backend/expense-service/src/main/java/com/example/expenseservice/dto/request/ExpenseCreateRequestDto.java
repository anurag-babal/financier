package com.example.expenseservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ExpenseCreateRequestDto {
    @NotBlank(message = "User ID can't be blank")
    private String userId;
    @NotBlank(message = "Category can't be blank")
    private String category;
    private String description;
    @NotNull(message = "Amount can't be blank")
    private Double amount;
    @NotBlank(message = "Date can't be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in yyyy-MM-dd format")
    private String date;
}
