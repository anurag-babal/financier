package com.financier.transaction.application.service;

import com.financier.transaction.application.dto.DashboardSummaryDTO;
import com.financier.transaction.application.dto.TransactionDTO;
import com.financier.transaction.domain.model.Transaction;
import com.financier.transaction.domain.model.Transaction.TransactionType;
import com.financier.transaction.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDTO createTransaction(String userId, TransactionDTO dto) {
        Transaction transaction = mapToDomain(dto);
        transaction.setUserId(userId);
        Transaction saved = transactionRepository.save(transaction);
        return mapToDto(saved);
    }

    @Override
    public List<TransactionDTO> getTransactionsByUserId(String userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransactionById(String userId, UUID id) {
        return transactionRepository.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public TransactionDTO updateTransaction(String userId, UUID id, TransactionDTO dto) {
        return transactionRepository.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .map(existing -> {
                    Transaction transaction = mapToDomain(dto);
                    transaction.setId(id);
                    transaction.setUserId(userId);
                    return mapToDto(transactionRepository.save(transaction));
                })
                .orElse(null);
    }

    @Override
    public void deleteTransaction(String userId, UUID id) {
        transactionRepository.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .ifPresent(t -> transactionRepository.deleteById(id));
    }

    @Override
    public List<TransactionDTO> getRecentTransactions(String userId, int limit) {
        return transactionRepository.findRecentByUserId(userId, limit).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DashboardSummaryDTO getSummary(String userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> expenseByCategory = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));

        return DashboardSummaryDTO.builder()
                .totalBalance(totalIncome.subtract(totalExpenses))
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .expenseByCategory(expenseByCategory)
                .build();
    }

    private Transaction mapToDomain(TransactionDTO dto) {
        return Transaction.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .type(dto.getType())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .date(dto.getDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    private TransactionDTO mapToDto(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .category(transaction.getCategory())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
