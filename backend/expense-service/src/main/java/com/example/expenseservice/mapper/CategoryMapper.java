package com.example.expenseservice.mapper;

import com.example.expenseservice.domain.model.Category;
import com.example.expenseservice.dto.request.CategoryCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category mapToCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        return Category.builder()
                .name(categoryCreateRequestDto.getName())
                .build();
    }
}
