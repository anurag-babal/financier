package com.example.transactionservice.data.dao;

import com.example.transactionservice.data.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionEntityDao extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByFromAccount(Long fromAccountId);

    List<TransactionEntity> findAllByFromAccountAndCategoryId(Long fromAccountId, Long categoryId);
}
