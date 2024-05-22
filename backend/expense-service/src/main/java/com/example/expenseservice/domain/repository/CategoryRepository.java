package com.example.expenseservice.domain.repository;

import com.example.expenseservice.domain.model.Category;

import java.util.List;

public interface CategoryRepository {
    Category save(Category category);
    Category update(Long id, Category category);
    Category findById(Long id);
    List<Category> findAll();
    void deleteById(Long id);
}
