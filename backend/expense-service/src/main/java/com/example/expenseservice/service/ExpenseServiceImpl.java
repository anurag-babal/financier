package com.example.expenseservice.service;

import com.example.expenseservice.domain.model.Expense;
import com.example.expenseservice.domain.repository.ExpenseRepository;
import com.example.expenseservice.domain.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense getExpense(Long expenseId) {
        return null;
    }

    @Override
    public Expense updateExpense(Long expenseId, Expense expense) {
        return null;
    }

    @Override
    public void deleteExpense(Long expenseId) {

    }

    @Override
    public List<Expense> getExpensesByUserId(String userId) {
        return null;
    }

    @Override
    public List<Expense> getExpensesByUserIdAndCategory(Long userId, Long categoryId) {
        return null;
    }
}
