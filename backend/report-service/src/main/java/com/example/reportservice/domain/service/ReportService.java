package com.example.reportservice.domain.service;

import com.example.reportservice.domain.model.Report;
import com.example.reportservice.dto.CategoryDto;
import com.example.reportservice.dto.ExpenseDto;

import java.util.List;

public interface ReportService {
    List<ExpenseDto> getExpensesReport(String userId);

    List<CategoryDto> getCategories();

    Report generateReport(List<ExpenseDto> expenses, List<CategoryDto> categories, String category, String year, String month);
}
