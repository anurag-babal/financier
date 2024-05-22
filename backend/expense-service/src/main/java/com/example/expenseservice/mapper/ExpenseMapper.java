package com.example.expenseservice.mapper;

import com.example.expenseservice.domain.model.Expense;
import com.example.expenseservice.dto.request.ExpenseCreateRequestDto;
import com.example.expenseservice.dto.response.ExpenseCreateResponseDto;
import com.example.expenseservice.util.DateFormatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExpenseMapper {
    public Expense toExpense(ExpenseCreateRequestDto expenseCreateRequestDto) {
        return Expense.builder()
                .userId(expenseCreateRequestDto.getUserId())
                .category(expenseCreateRequestDto.getCategory())
                .description(expenseCreateRequestDto.getDescription())
                .amount(BigDecimal.valueOf(expenseCreateRequestDto.getAmount()))
                .date(DateFormatter.stringToLocalDate(expenseCreateRequestDto.getDate()))
                .build();
    }

    public ExpenseCreateResponseDto toExpenseCreateResponseDto(Expense expense) {
        return ExpenseCreateResponseDto.builder()
                .id(expense.getId())
                .userId(expense.getUserId())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .amount(expense.getAmount().doubleValue())
                .date(DateFormatter.localDateToString(expense.getDate()))
                .build();
    }
}
