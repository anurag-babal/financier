package com.example.expenseservice.data;

import com.example.expenseservice.data.dao.CategoryEntityDao;
import com.example.expenseservice.data.dao.ExpenseEntityDao;
import com.example.expenseservice.data.entity.CategoryEntity;
import com.example.expenseservice.data.entity.ExpenseEntity;
import com.example.expenseservice.domain.model.Expense;
import com.example.expenseservice.domain.repository.ExpenseRepository;
import com.example.expenseservice.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final ExpenseEntityDao expenseEntityDao;
    private final CategoryEntityDao categoryEntityDao;

    @Override
    public List<Expense> findAll() {
        return expenseEntityDao.findAll().stream()
                .map(this::mapToExpense)
                .toList();
    }

    @Override
    public Expense save(Expense expense) {
        ExpenseEntity expenseEntity = mapToExpenseEntity(expense);
        return mapToExpense(expenseEntityDao.save(expenseEntity));
    }

    @Override
    public Expense update(Long expenseId, Expense expense) {
        ExpenseEntity expenseEntity = mapToExpenseEntity(expense);
        expenseEntity.setId(expenseId);
        return mapToExpense(expenseEntityDao.save(expenseEntity));
    }

    @Override
    public void deleteById(Long id) {
        expenseEntityDao.deleteById(id);
    }

    @Override
    public Expense findById(Long id) {
        return mapToExpense(findExpenseEntityById(id));
    }

    @Override
    public List<Expense> findAllByUserId(String userId) {
        return expenseEntityDao.findAllByUserId(userId).stream()
                .map(this::mapToExpense)
                .toList();
    }

    public List<Expense> findAllByUserIdAndCategory(String userId, Long categoryId) {
        CategoryEntity categoryEntity = categoryEntityDao.findById(categoryId).orElse(null);
        return expenseEntityDao.findAllByUserIdAndCategoryEntity(userId, categoryEntity).stream()
                .map(this::mapToExpense)
                .toList();
    }

    public List<ExpenseEntity> findByUserIdAndCategoryIdAndDateBetween(Long userId, Long categoryId, String startDate, String endDate) {
        return null;
    }

    public List<ExpenseEntity> findByUserIdAndDateBetween(Long userId, String startDate, String endDate) {
        return null;
    }

    public List<ExpenseEntity> findByDateBetween(String startDate, String endDate) {
        return null;
    }

    private ExpenseEntity findExpenseEntityById(Long id) {
        return expenseEntityDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Expense", "id", id.toString())
        );
    }

    private CategoryEntity findCategoryEntityByName(String categoryName) {
        return categoryEntityDao.findByName(categoryName).orElseThrow(
                () -> new ResourceNotFoundException("Category", "name", categoryName)
        );
    }

    private CategoryEntity findCategoryEntityById(Long categoryId) {
        return categoryEntityDao.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId.toString())
        );
    }

    private Expense mapToExpense(ExpenseEntity expenseEntity) {
        return Expense.builder()
                .id(expenseEntity.getId())
                .accountId(expenseEntity.getAccountId())
                .userId(expenseEntity.getUserId())
                .category(mapToCategoryName(expenseEntity.getCategoryEntity().getId()))
                .amount(expenseEntity.getAmount())
                .date(expenseEntity.getDate())
                .description(expenseEntity.getDescription())
                .build();
    }

    private String mapToCategoryName(Long categoryId) {
        return findCategoryEntityById(categoryId).getName();
    }

    private ExpenseEntity mapToExpenseEntity(Expense expense) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setAccountId(expense.getAccountId());
        expenseEntity.setUserId(expense.getUserId());
        expenseEntity.setAmount(expense.getAmount());
        expenseEntity.setDate(expense.getDate());
        expenseEntity.setDescription(expense.getDescription());
        expenseEntity.setCategoryEntity(mapToCategoryEntity(expense.getCategory()));
        return expenseEntity;
    }

    private CategoryEntity mapToCategoryEntity(String categoryName) {
        return findCategoryEntityByName(categoryName);
    }
}
