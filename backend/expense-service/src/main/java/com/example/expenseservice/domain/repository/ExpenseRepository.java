package com.example.expenseservice.domain.repository;

import com.example.expenseservice.domain.model.Expense;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseRepository {
    Expense save(Expense expense);

    Expense update(Long expenseId, Expense expense);

    void deleteById(Long id);

    Expense findById(Long id);

    List<Expense> findAll();

    List<Expense> findAllByUserId(String userId);

    List<Expense> findAllByUserIdAndCategory(String userId, Long categoryId, Pageable pageable);

    List<Expense> findAllByUserIdAndMonthAndYear(String userId, String month, String year);

    List<Expense> findAllByUserIdPageable(String userId, Pageable pageable);
}
