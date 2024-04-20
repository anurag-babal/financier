package com.example.transactionservice.data;

import com.example.transactionservice.data.dao.TransactionEntityDao;
import com.example.transactionservice.data.entity.TransactionEntity;
import com.example.transactionservice.domain.model.Transaction;
import com.example.transactionservice.domain.model.TransactionStatus;
import com.example.transactionservice.domain.model.TransactionType;
import com.example.transactionservice.domain.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionEntityDao transactionEntityDao;

    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = mapToTransactionEntity(transaction);
        transactionEntity = saveTransactionEntity(transactionEntity);
        return mapToTransaction(transactionEntity);
    }

    public Transaction update(Long transactionId, Transaction transaction) {
        TransactionEntity transactionEntity = mapToTransactionEntity(transaction);
        transactionEntity.setId(transactionId);
        transactionEntity = saveTransactionEntity(transactionEntity);
        return mapToTransaction(transactionEntity);
    }

    public void deleteById(Long id) {
        transactionEntityDao.deleteById(id);
    }

    public Transaction findById(Long id) {
        TransactionEntity transactionEntity = findTransactionEntityById(id);
        return mapToTransaction(transactionEntity);
    }

    public List<Transaction> findAll() {
        List<TransactionEntity> transactionEntities = transactionEntityDao.findAll();
        return mapToTransactions(transactionEntities);
    }

    public List<Transaction> findAllByFromAccount(Long fromAccountId) {
        List<TransactionEntity> transactionEntities = transactionEntityDao.findAllByFromAccount(fromAccountId);
        return mapToTransactions(transactionEntities);
    }

    public List<Transaction> findAllByFromAccountAndCategory(Long fromAccountId, Long categoryId) {
        List<TransactionEntity> transactionEntities = transactionEntityDao
                .findAllByFromAccountAndCategoryId(fromAccountId, categoryId);
        return mapToTransactions(transactionEntities);
    }

    private TransactionEntity findTransactionEntityById(Long id) {
        return transactionEntityDao
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    private TransactionEntity saveTransactionEntity(TransactionEntity transactionEntity) {
        return transactionEntityDao.save(transactionEntity);
    }

    private Transaction mapToTransaction(TransactionEntity transactionEntity) {
        return Transaction.builder()
                .id(transactionEntity.getId())
                .amount(transactionEntity.getAmount())
                .fromAccountId(transactionEntity.getFromAccount())
                .toAccountId(transactionEntity.getToAccount())
                .type(TransactionType.valueOf(transactionEntity.getTransactionType()))
                .status(TransactionStatus.valueOf(transactionEntity.getStatus()))
                .transactionDate(transactionEntity.getTransactionDate())
                .categoryId(transactionEntity.getCategoryId())
                .description(transactionEntity.getDescription())
                .build();
    }

    private TransactionEntity mapToTransactionEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .amount(transaction.getAmount())
                .fromAccount(transaction.getFromAccountId())
                .toAccount(transaction.getToAccountId())
                .transactionType(transaction.getType().toString())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus().toString())
                .categoryId(transaction.getCategoryId())
                .description(transaction.getDescription())
                .build();
    }

    public List<Transaction> mapToTransactions(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .map(this::mapToTransaction)
                .toList();
    }
}
