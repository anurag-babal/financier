package com.financier.transaction.data.repository;

import com.financier.transaction.data.entity.TransactionEntity;
import com.financier.transaction.domain.model.Transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaTransactionRepositoryTest {

    @Autowired
    private JpaTransactionRepository jpaTransactionRepository;

    @Test
    void shouldSaveAndFindTransactionsByUserId() {
        // Given
        TransactionEntity t1 = TransactionEntity.builder()
                .userId("user-123")
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("100.00"))
                .category("Food")
                .date(LocalDateTime.now())
                .build();
        
        TransactionEntity t2 = TransactionEntity.builder()
                .userId("user-123")
                .type(TransactionType.INCOME)
                .amount(new BigDecimal("500.00"))
                .category("Salary")
                .date(LocalDateTime.now())
                .build();

        jpaTransactionRepository.save(t1);
        jpaTransactionRepository.save(t2);

        // When
        List<TransactionEntity> transactions = jpaTransactionRepository.findByUserId("user-123");

        // Then
        assertThat(transactions).hasSize(2);
        assertThat(transactions).extracting(TransactionEntity::getCategory).containsExactlyInAnyOrder("Food", "Salary");
    }

    @Test
    void shouldFindRecentTransactions() {
        // Given
        String userId = "user-123";
        for (int i = 0; i < 10; i++) {
            jpaTransactionRepository.save(TransactionEntity.builder()
                    .userId(userId)
                    .type(TransactionType.EXPENSE)
                    .amount(new BigDecimal(10 + i))
                    .category("Cat " + i)
                    .date(LocalDateTime.now().minusDays(i))
                    .build());
        }

        // When
        List<TransactionEntity> recent = jpaTransactionRepository.findByUserIdOrderByDateDesc(userId, PageRequest.of(0, 5));

        // Then
        assertThat(recent).hasSize(5);
        assertThat(recent.get(0).getCategory()).isEqualTo("Cat 0"); // Most recent
    }
}
