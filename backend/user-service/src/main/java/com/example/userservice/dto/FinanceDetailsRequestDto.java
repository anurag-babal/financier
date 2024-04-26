package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FinanceDetailsRequestDto {
    @NotBlank(message = "UserId is mandatory")
    private int userId;
}
