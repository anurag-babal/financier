package com.financier.transaction.data.repository;

import com.financier.transaction.data.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByUserId(String userId);
    List<TransactionEntity> findByUserIdOrderByDateDesc(String userId, Pageable pageable);
}
