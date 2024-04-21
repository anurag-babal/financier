package com.example.transactionservice.domain.repository;

import com.example.transactionservice.domain.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Transaction update(Long transactionId, Transaction transaction);
    void deleteById(Long id);
    Transaction findById(Long id);
    List<Transaction> findAll();
    List<Transaction> findAllByFromAccount(Long fromAccountId);
    List<Transaction> findAllByFromAccountAndCategory(Long fromAccountId, Long categoryId);
}
