package com.example.expenseservice.data.dao;

import com.example.expenseservice.data.entity.CategoryEntity;
import com.example.expenseservice.data.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ExpenseEntityDao extends JpaRepository<ExpenseEntity, Long> {
    Page<ExpenseEntity> findAllByUserId(String userId, Pageable pageable);

    List<ExpenseEntity> findAllByUserId(String userId);

    Page<ExpenseEntity> findAllByUserIdAndCategoryEntity(String userId, CategoryEntity categoryEntity, Pageable pageable);

    List<ExpenseEntity> findAllByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);

}
