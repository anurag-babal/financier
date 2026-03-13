package com.financier.transaction.data.adapter;

import com.financier.transaction.domain.model.Transaction;
import com.financier.transaction.domain.repository.TransactionRepository;
import com.financier.transaction.data.entity.TransactionEntity;
import com.financier.transaction.data.mapper.TransactionMapper;
import com.financier.transaction.data.repository.JpaTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostgresTransactionRepository implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = transactionMapper.toEntity(transaction);
        TransactionEntity saved = jpaTransactionRepository.save(entity);
        return transactionMapper.toDomain(saved);
    }

    @Override
    public List<Transaction> findByUserId(String userId) {
        return jpaTransactionRepository.findByUserId(userId).stream()
                .map(transactionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpaTransactionRepository.findById(id).map(transactionMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaTransactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findRecentByUserId(String userId, int limit) {
        return jpaTransactionRepository.findByUserIdOrderByDateDesc(userId, PageRequest.of(0, limit)).stream()
                .map(transactionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
