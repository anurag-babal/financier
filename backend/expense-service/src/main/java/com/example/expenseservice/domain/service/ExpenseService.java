package com.example.expenseservice.domain.service;

import com.example.expenseservice.domain.model.Expense;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(Expense expense);

    Expense getExpense(Long expenseId);

    Expense updateExpense(Long expenseId, Expense expense);

    void deleteExpense(Long expenseId);

    List<Expense> getExpensesByUserId(String userId, Pageable pageable);

    List<Expense> getExpensesByUserIdAndCategory(String userId, Long categoryId, Pageable pageable);

    List<Expense> getExpensesByUserIdAndMonthAndYear(String userId, String month, String year);

    List<Expense> getAllExpensesByUserId(String userId);
}
