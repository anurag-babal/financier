package com.example.expenseservice.domain.service;

import com.example.expenseservice.domain.model.Expense;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(Expense expense);
    Expense getExpense(Long expenseId);
    Expense updateExpense(Long expenseId, Expense expense);
    void deleteExpense(Long expenseId);
    List<Expense> getExpensesByUserId(String userId);
    List<Expense> getExpensesByUserIdAndCategory(Long userId, Long categoryId);
}
