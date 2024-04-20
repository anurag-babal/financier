package com.example.transactionservice.controller;

import com.example.transactionservice.Dto.TransactionCreateRequestDto;
import com.example.transactionservice.Dto.TransactionResponseDto;
import com.example.transactionservice.domain.model.Transaction;
import com.example.transactionservice.domain.service.TransactionService;
import com.example.transactionservice.mapper.TransactionMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransaction(
            @PathVariable Long id
    ) {
        Transaction transaction = transactionService.getTransaction(id);
        return ResponseEntity.ok(transactionMapper.mapToTransactionResponseDto(transaction));
    }

    @GetMapping("/accounts/{fromAccountId}")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByFromAccount(
            @PathVariable Long fromAccountId
    ) {
        List<Transaction> transactions = transactionService.getTransactionsByFromAccount(fromAccountId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionResponseDtoList(transactions));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(
            @Valid @RequestBody TransactionCreateRequestDto transactionCreateRequestDto
    ) {
        Transaction transaction = transactionMapper.mapToTransaction(transactionCreateRequestDto);
        transaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(transactionMapper.mapToTransactionResponseDto(transaction));
    }
}
