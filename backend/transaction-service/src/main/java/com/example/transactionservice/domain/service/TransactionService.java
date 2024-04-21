package com.example.transactionservice.domain.service;

import com.example.transactionservice.domain.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    Transaction getTransaction(Long transactionId);

    Transaction updateTransaction(Long transactionId, Transaction transaction);

    void deleteTransaction(Long transactionId);

    List<Transaction> getTransactionsByFromAccount(Long fromAccountId);

    List<Transaction> getTransactionsByFromAccountAndCategory(Long fromAccountId, Long categoryId);
}
