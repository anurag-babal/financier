package com.example.transactionservice.domain.repository;

import com.example.transactionservice.domain.model.Expense;

import java.util.List;

public interface ExpenseRepository {
    Expense save(Expense expense);
    Expense update(Long expenseId, Expense expense);
    void deleteById(Long id);
    Expense findById(Long id);
    List<Expense> findAll();
    List<Expense> findAllByAccount(Long accountId);
    List<Expense> findAllByAccountAndCategory(Long accountId, Long categoryId);
}
