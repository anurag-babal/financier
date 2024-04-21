package com.example.transactionservice.service;

import com.example.transactionservice.domain.model.Transaction;
import com.example.transactionservice.domain.repository.TransactionRepository;
import com.example.transactionservice.domain.service.TransactionService;
import com.example.transactionservice.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public Transaction updateTransaction(Long transactionId, Transaction transaction) {
        return transactionRepository.update(transactionId, transaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<Transaction> getTransactionsByFromAccount(Long fromAccountId) {
        return transactionRepository.findAllByFromAccount(fromAccountId);
    }

    @Override
    public List<Transaction> getTransactionsByFromAccountAndCategory(Long fromAccountId, Long categoryId) {
        return transactionRepository.findAllByFromAccountAndCategory(fromAccountId, categoryId);
    }
}
