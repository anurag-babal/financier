package com.example.reportservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Report {
    private String name;
    private String description;
    private List<CategoryExpensesGroup> content;
    private String createdAt;
}
