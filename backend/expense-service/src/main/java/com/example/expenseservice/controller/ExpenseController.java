package com.example.expenseservice.controller;

import com.example.expenseservice.domain.model.Expense;
import com.example.expenseservice.domain.service.ExpenseService;
import com.example.expenseservice.dto.request.ExpenseCreateRequestDto;
import com.example.expenseservice.dto.response.ResponseDto;
import com.example.expenseservice.mapper.ExpenseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createExpense(@Valid @RequestBody ExpenseCreateRequestDto expenseCreateRequestDto) {
        Expense expense = expenseMapper.toExpense(expenseCreateRequestDto);
        Expense createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED,
                        "Expense added successfully",
                        createdExpense
                ),
                HttpStatus.CREATED
        );
    }
}
