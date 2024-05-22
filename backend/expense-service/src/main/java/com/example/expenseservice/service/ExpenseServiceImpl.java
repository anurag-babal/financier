package com.example.expenseservice.service;

import com.example.expenseservice.domain.model.Expense;
import com.example.expenseservice.domain.repository.ExpenseRepository;
import com.example.expenseservice.domain.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        return expenseRepository.findById(expenseId);
    }

    @Override
    public Expense updateExpense(Long expenseId, Expense expense) {
        return expenseRepository.update(expenseId, expense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public List<Expense> getExpensesByUserId(String userId, Pageable pageable) {
        return expenseRepository.findAllByUserIdPageable(userId, pageable);
    }

    @Override
    public List<Expense> getExpensesByUserIdAndCategory(String userId, Long categoryId, Pageable pageable) {
        return expenseRepository.findAllByUserIdAndCategory(userId, categoryId, pageable);
    }

    @Override
    public List<Expense> getExpensesByUserIdAndMonthAndYear(String userId, String month, String year) {
        return expenseRepository.findAllByUserIdAndMonthAndYear(userId, month, year);
    }

    @Override
    public List<Expense> getAllExpensesByUserId(String userId) {
        return expenseRepository.findAllByUserId(userId);
    }
}
