package com.example.expenseservice.controller;

import com.example.expenseservice.domain.model.Category;
import com.example.expenseservice.domain.service.CategoryService;
import com.example.expenseservice.dto.request.CategoryCreateRequestDto;
import com.example.expenseservice.dto.response.ResponseDto;
import com.example.expenseservice.mapper.CategoryMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createCategory(@Valid @RequestBody CategoryCreateRequestDto category) {
        categoryService.createCategory(categoryMapper.mapToCategory(category));
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED,
                        "Category created successfully",
                        category
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryCreateRequestDto category
    ) {
        categoryService.updateCategory(id, categoryMapper.mapToCategory(category));
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Category updated successfully",
                        category
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Category fetched successfully",
                        category
                ),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Categories fetched successfully",
                        categories
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Category deleted successfully",
                        true
                ),
                HttpStatus.OK
        );
    }
}
