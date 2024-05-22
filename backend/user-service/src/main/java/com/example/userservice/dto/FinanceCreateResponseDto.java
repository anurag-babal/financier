package com.example.userservice.dto;

import com.example.userservice.data.entities.UserEntity;
import lombok.Data;

@Data
public class FinanceCreateResponseDto {
    private int id;
    private int userId;
    private double budget;
    private double savings;
}
