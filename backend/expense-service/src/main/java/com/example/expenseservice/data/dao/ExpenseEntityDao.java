package com.example.expenseservice.data.dao;

import com.example.expenseservice.data.entity.CategoryEntity;
import com.example.expenseservice.data.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ExpenseEntityDao extends JpaRepository<ExpenseEntity, Long> {
    Collection<ExpenseEntity> findAllByUserId(String userId);

    List<ExpenseEntity> findAllByUserIdAndCategoryEntity(String userId, CategoryEntity categoryEntity);
}
