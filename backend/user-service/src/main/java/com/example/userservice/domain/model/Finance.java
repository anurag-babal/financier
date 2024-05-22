package com.example.userservice.domain.model;

import lombok.Data;

@Data
public class Finance {
    private int id;
    private int userId;
    private double budget;
    private double savings;
}
