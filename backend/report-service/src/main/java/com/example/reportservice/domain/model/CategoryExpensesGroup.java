package com.example.reportservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryExpensesGroup {
    private String groupBy;
    private List<CategoryExpense> categoryExpenses;
}
