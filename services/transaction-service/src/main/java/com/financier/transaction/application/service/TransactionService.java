package com.financier.transaction.application.service;

import com.financier.transaction.application.dto.DashboardSummaryDTO;
import com.financier.transaction.application.dto.TransactionDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO createTransaction(String userId, TransactionDTO transactionDTO);
    List<TransactionDTO> getTransactionsByUserId(String userId);
    TransactionDTO getTransactionById(String userId, UUID id);
    TransactionDTO updateTransaction(String userId, UUID id, TransactionDTO transactionDTO);
    void deleteTransaction(String userId, UUID id);
    List<TransactionDTO> getRecentTransactions(String userId, int limit);
    DashboardSummaryDTO getSummary(String userId);
}
