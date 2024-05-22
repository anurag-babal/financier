package com.example.reportservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryExpense {
    private String category;
    private Double total;
}
