package com.example.userservice.domain.model;

import lombok.Data;

@Data
public class Finance {
    private int id;
    private int userId;
    private int budget;
    private int savings;
}
