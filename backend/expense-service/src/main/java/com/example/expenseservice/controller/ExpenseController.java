package com.example.expenseservice.controller;

import com.example.expenseservice.domain.model.Expense;
import com.example.expenseservice.domain.service.ExpenseService;
import com.example.expenseservice.dto.request.ExpenseCreateRequestDto;
import com.example.expenseservice.dto.response.ResponseDto;
import com.example.expenseservice.mapper.ExpenseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getExpense(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Expense fetched successfully",
                        expenseService.getExpense(id)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllExpenses(
            @RequestParam String userId,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable
    ) {
        List<Expense> expenses;
        if (categoryId != null) {
            expenses = expenseService.getExpensesByUserIdAndCategory(userId, categoryId, pageable);
        } else {
            expenses = expenseService.getExpensesByUserId(userId, pageable);
        }

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Expenses fetched successfully",
                        expenses
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllExpenses(@RequestParam String userId) {
        List<Expense> expenses = expenseService.getAllExpensesByUserId(userId);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Expenses fetched successfully",
                        expenses
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseCreateRequestDto expenseCreateRequestDto) {
        Expense expense = expenseMapper.toExpense(expenseCreateRequestDto);
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Expense updated successfully",
                        updatedExpense
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Expense deleted successfully",
                        null
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/sum")
    public ResponseEntity<ResponseDto> getSumOfExpenses(
            @RequestParam String userId,
            @RequestParam String month,
            @RequestParam String year
    ) {
        List<Expense> expenses = expenseService.getExpensesByUserIdAndMonthAndYear(userId, month, year);
        double sum = expenses.stream().mapToDouble(expense -> expense.getAmount().doubleValue()).sum();
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Sum of expenses fetched successfully",
                        sum
                ),
                HttpStatus.OK
        );
    }

}
