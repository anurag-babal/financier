package com.example.userservice.dto;

import com.example.userservice.data.entities.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FinanceCreateRequestDto {
    @NotBlank(message = "UserId is mandatory")
    private int userId;

    @NotBlank(message = "Budget is mandatory")
    private int budget;

    @NotBlank(message = "Savings are mandatory")
    private int savings;
}
