package com.financier.transaction.domain.repository;

import com.financier.transaction.domain.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findByUserId(String userId);
    Optional<Transaction> findById(UUID id);
    void deleteById(UUID id);
    List<Transaction> findRecentByUserId(String userId, int limit);
}
