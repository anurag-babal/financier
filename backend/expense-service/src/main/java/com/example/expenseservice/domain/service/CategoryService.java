package com.example.expenseservice.domain.service;

import com.example.expenseservice.domain.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deleteCategoryById(Long id);
}
