package com.example.reportservice.service.client;

import com.example.reportservice.dto.CategoryResponseDto;
import com.example.reportservice.dto.ExpenseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("expense-service")
public interface ExpenseFeignClient {
    @GetMapping(value = "/api/v1/expenses/all")
    public ResponseEntity<ExpenseResponseDto> getAllExpenses(
            @RequestParam String userId
    );

    @GetMapping(value = "/api/v1/categories")
    public ResponseEntity<CategoryResponseDto> getAllCategories();
}
