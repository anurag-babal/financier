package com.example.expenseservice.data;

import com.example.expenseservice.data.dao.CategoryEntityDao;
import com.example.expenseservice.data.entity.CategoryEntity;
import com.example.expenseservice.domain.model.Category;
import com.example.expenseservice.domain.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryEntityDao categoryEntityDao;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = mapToCategoryEntity(category);
        categoryEntity = categoryEntityDao.save(categoryEntity);
        return mapToCategory(categoryEntity);
    }

    @Override
    public Category update(Long id, Category category) {
        CategoryEntity categoryEntity = mapToCategoryEntity(category);
        categoryEntity.setId(id);
        categoryEntity = categoryEntityDao.save(categoryEntity);
        return mapToCategory(categoryEntity);
    }

    @Override
    public Category findById(Long id) {
        CategoryEntity categoryEntity = findCategoryEntityById(id);
        return mapToCategory(categoryEntity);
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoryEntities = categoryEntityDao.findAll();
        return mapToCategories(categoryEntities);
    }

    @Override
    public void deleteById(Long id) {
        CategoryEntity categoryEntity = findCategoryEntityById(id);
        categoryEntityDao.delete(categoryEntity);
    }

    private CategoryEntity findCategoryEntityById(Long id) {
        return categoryEntityDao
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    private Category mapToCategory(CategoryEntity categoryEntity) {
        return Category.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }

    private CategoryEntity mapToCategoryEntity(Category category) {
        return CategoryEntity.builder()
                .name(category.getName())
                .build();
    }

    private List<Category> mapToCategories(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::mapToCategory)
                .collect(Collectors.toList());
    }
}
