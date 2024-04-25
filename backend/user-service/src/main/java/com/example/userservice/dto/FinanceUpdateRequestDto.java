package com.example.userservice.dto;

import lombok.Data;

@Data
public class FinanceUpdateRequestDto {
    private int userId;
    private int budget;
    private int savings;
}
