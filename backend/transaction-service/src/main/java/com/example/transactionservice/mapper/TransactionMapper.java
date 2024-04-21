package com.example.transactionservice.mapper;

import com.example.transactionservice.dto.TransactionCreateRequestDto;
import com.example.transactionservice.dto.TransactionResponseDto;
import com.example.transactionservice.domain.model.Transaction;
import com.example.transactionservice.domain.model.TransactionStatus;
import com.example.transactionservice.domain.model.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TransactionMapper {

    public Transaction mapToTransaction(TransactionCreateRequestDto transactionCreateRequestDto) {
        return Transaction.builder()
                .status(TransactionStatus.PENDING)
                .type(TransactionType.valueOf(transactionCreateRequestDto.getTransactionType().toUpperCase()))
                .fromAccountId(transactionCreateRequestDto.getFromAccountId())
                .toAccountId(transactionCreateRequestDto.getToAccountId())
                .categoryId(transactionCreateRequestDto.getCategoryId())
                .amount(BigDecimal.valueOf(transactionCreateRequestDto.getAmount()))
                .transactionDate(LocalDateTime.now())
                .build();
    }

    public TransactionResponseDto mapToTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .transactionId(transaction.getId())
                .transactionStatus(TransactionStatus.PENDING.toString())
                .transactionType(transaction.getType().toString())
                .fromAccountId(transaction.getFromAccountId())
                .toAccountId(transaction.getToAccountId())
                .categoryId(transaction.getCategoryId())
                .amount(transaction.getAmount().doubleValue())
                .timestamp(transaction.getTransactionDate().toString())
                .build();
    }

    public List<TransactionResponseDto> mapToTransactionResponseDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::mapToTransactionResponseDto)
                .toList();
    }
}
