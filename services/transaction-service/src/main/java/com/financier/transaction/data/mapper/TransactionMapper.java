package com.financier.transaction.data.mapper;

import com.financier.transaction.domain.model.Transaction;
import com.financier.transaction.data.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return Transaction.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .type(entity.getType())
                .amount(entity.getAmount())
                .category(entity.getCategory())
                .description(entity.getDescription())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public TransactionEntity toEntity(Transaction domain) {
        if (domain == null) return null;
        return TransactionEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .type(domain.getType())
                .amount(domain.getAmount())
                .category(domain.getCategory())
                .description(domain.getDescription())
                .date(domain.getDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
