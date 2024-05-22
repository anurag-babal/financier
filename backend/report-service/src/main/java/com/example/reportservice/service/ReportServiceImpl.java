package com.example.reportservice.service;

import com.example.reportservice.domain.model.CategoryExpense;
import com.example.reportservice.domain.model.CategoryExpensesGroup;
import com.example.reportservice.domain.model.Report;
import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.dto.CategoryDto;
import com.example.reportservice.dto.CategoryResponseDto;
import com.example.reportservice.dto.ExpenseDto;
import com.example.reportservice.dto.ExpenseResponseDto;
import com.example.reportservice.service.client.ExpenseFeignClient;
import com.example.reportservice.service.client.TransactionFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionFeignClient transactionFeignClient;
    private final ExpenseFeignClient expenseFeignClient;

    @Override
    public List<ExpenseDto> getExpensesReport(String userId) {
        ResponseEntity<ExpenseResponseDto> expenses = expenseFeignClient.getAllExpenses(userId);
        if (expenses != null && expenses.getBody() != null) {
            return expenses.getBody().getData();
        }
        return null;
    }

    @Override
    public List<CategoryDto> getCategories() {
        ResponseEntity<CategoryResponseDto> categories = expenseFeignClient.getAllCategories();
        if (categories != null && categories.getBody() != null) {
            return categories.getBody().getData();
        }
        return null;
    }

    @Override
    public Report generateReport(List<ExpenseDto> expenses, List<CategoryDto> categories, String category, String year, String month) {
        Map<String, List<Map<String, Double>>> content = new HashMap<>();
        Map<String, Double> categoryExpense = new HashMap<>();
        if (!year.equalsIgnoreCase("all")) {
            List<ExpenseDto> filteredExpenses = expenses.stream()
                    .filter(expense -> expense.getDate().getYear() == Integer.parseInt(year))
                    .toList();
            if (!month.equalsIgnoreCase("all")) {
                List<ExpenseDto> filteredExpensesByMonth = filteredExpenses.stream()
                        .filter(expense -> expense.getDate().getMonth().toString().equalsIgnoreCase(month))
                        .toList();
                if (category.equalsIgnoreCase("all")) {
                    for (ExpenseDto expense : filteredExpensesByMonth) {
                        String day = expense.getDate().format(DateTimeFormatter.ofPattern("dd"));
                        calculateCategorySeparate(content, expense, day);
                    }
                } else {
                    List<ExpenseDto> filteredExpensesByCategory = filteredExpensesByMonth.stream()
                            .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                            .toList();
                    for (ExpenseDto expense : filteredExpensesByCategory) {
                        String day = expense.getDate().format(DateTimeFormatter.ofPattern("dd"));
                        content.put(day, content.getOrDefault(day, new ArrayList<>()));
                        content.get(day).add(Map.of(expense.getCategory(), expense.getAmount().doubleValue()));
                    }
                }
            } else {
                calculateCategoryExpense(filteredExpenses, category, content, categoryExpense);
            }
        } else {
            if (!month.equalsIgnoreCase("all")) {
                List<ExpenseDto> filteredExpensesByMonth = expenses.stream()
                        .filter(expense -> expense.getDate().getMonth().toString().equalsIgnoreCase(month))
                        .toList();
                if (category.equalsIgnoreCase("all")) {
                    for (ExpenseDto expense : filteredExpensesByMonth) {
                        String yearName = String.valueOf(expense.getDate().getYear());
                        calculateCategorySeparate(content, expense, yearName);
                    }
                } else {
                    List<ExpenseDto> filteredExpensesByCategory = filteredExpensesByMonth.stream()
                            .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                            .toList();
                    for (ExpenseDto expense : filteredExpensesByCategory) {
                        String yearName = String.valueOf(expense.getDate().getYear());
                        content.put(
                                yearName,
                                content.getOrDefault(yearName, new ArrayList<>())
                        );
                        content.get(yearName).add(Map.of(expense.getCategory(), expense.getAmount().doubleValue()));
                    }
                }
            } else {
                calculateCategoryExpense(expenses, category, content, categoryExpense);
            }
        }
        List<CategoryExpensesGroup> categoryExpensesGroups = new ArrayList<>();

        for (Map.Entry<String, List<Map<String, Double>>> entry : content.entrySet()) {
            List<CategoryExpense> categoryExpenses = new ArrayList<>();
            for (Map<String, Double> categoryEntry : entry.getValue()) {
                for (Map.Entry<String, Double> categoryEntry2 : categoryEntry.entrySet()) {
                    categoryExpenses.add(new CategoryExpense(categoryEntry2.getKey(), categoryEntry2.getValue()));
                }
            }
            // check categoryExpenses contains all categories from categories list and add missing categories with 0.0 amount
            for (CategoryDto categoryDto : categories) {
                boolean found = false;
                for (CategoryExpense categoryExpense2 : categoryExpenses) {
                    if (categoryDto.getName().equalsIgnoreCase(categoryExpense2.getCategory())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    categoryExpenses.add(new CategoryExpense(categoryDto.getName(), 0.0));
                }
            }
            categoryExpensesGroups.add(new CategoryExpensesGroup(entry.getKey(), categoryExpenses));
        }
        return new Report(category, "desc", categoryExpensesGroups, LocalDateTime.now().toString());
    }

    private void calculateCategorySeparate(Map<String, List<Map<String, Double>>> content, ExpenseDto expense, String day) {
        List<Map<String, Double>> newList;
        Map<String, Double> newMap = new HashMap<>();

        if (content.containsKey(day)) {
            newList = new ArrayList<>(content.get(day));
            boolean foundCategory = false;
            for (Map<String, Double> map : newList) {
                if (map.containsKey(expense.getCategory())) {
                    newMap = map;
                    foundCategory = true;
                    break;
                }
            }
            if (!foundCategory) {
                newMap = new HashMap<>();
                newList.add(newMap);
            }
            newMap.put(expense.getCategory(), newMap.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount().doubleValue());
        } else {
            newList = new ArrayList<>();
            newMap = new HashMap<>();
            newMap.put(expense.getCategory(), expense.getAmount().doubleValue());
            newList.add(newMap);
        }

        content.put(day, newList);
    }

    private void calculateCategoryExpense(
            List<ExpenseDto> expenses,
            String category,
            Map<String, List<Map<String, Double>>> content,
            Map<String, Double> categoryExpense
    ) {
        if (category.equalsIgnoreCase("all")) {
            for (ExpenseDto expense : expenses) {
                String monthName = expense.getDate().getMonth().toString();
                calculateCategorySeparate(content, expense, monthName);
            }
        } else {
            List<ExpenseDto> filteredExpensesByCategory = expenses.stream()
                    .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                    .toList();
            for (ExpenseDto expense : filteredExpensesByCategory) {
                String monthName = expense.getDate().getMonth().toString();
                content.put(
                        monthName,
                        content.getOrDefault(monthName, new ArrayList<>())
                );
                content.get(monthName).add(Map.of(expense.getCategory(), expense.getAmount().doubleValue()));
            }
        }
    }
}
