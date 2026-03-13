package com.financier.transaction.application.service;

import com.financier.transaction.application.dto.DashboardSummaryDTO;
import com.financier.transaction.application.dto.TransactionDTO;
import com.financier.transaction.domain.model.Transaction;
import com.financier.transaction.domain.model.Transaction.TransactionType;
import com.financier.transaction.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldCalculateCorrectSummary() {
        // Given
        String userId = "user-123";
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().type(TransactionType.INCOME).amount(new BigDecimal("1000")).category("Salary").build(),
                Transaction.builder().type(TransactionType.EXPENSE).amount(new BigDecimal("200")).category("Food").build(),
                Transaction.builder().type(TransactionType.EXPENSE).amount(new BigDecimal("300")).category("Rent").build(),
                Transaction.builder().type(TransactionType.EXPENSE).amount(new BigDecimal("100")).category("Food").build()
        );

        when(transactionRepository.findByUserId(userId)).thenReturn(transactions);

        // When
        DashboardSummaryDTO summary = transactionService.getSummary(userId);

        // Then
        assertThat(summary.getTotalIncome()).isEqualByComparingTo("1000");
        assertThat(summary.getTotalExpenses()).isEqualByComparingTo("600");
        assertThat(summary.getTotalBalance()).isEqualByComparingTo("400");
        
        Map<String, BigDecimal> byCategory = summary.getExpenseByCategory();
        assertThat(byCategory.get("Food")).isEqualByComparingTo("300");
        assertThat(byCategory.get("Rent")).isEqualByComparingTo("300");
    }
}
