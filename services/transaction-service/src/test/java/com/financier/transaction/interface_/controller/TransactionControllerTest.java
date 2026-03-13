package com.financier.transaction.interface_.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financier.transaction.application.dto.DashboardSummaryDTO;
import com.financier.transaction.application.dto.TransactionDTO;
import com.financier.transaction.domain.model.Transaction.TransactionType;
import com.financier.transaction.application.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.financier.transaction.TransactionServiceApplication.API_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTransaction() throws Exception {
        // Given
        String userId = "user-123";
        TransactionDTO dto = TransactionDTO.builder()
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("50.00"))
                .category("Coffee")
                .date(LocalDateTime.now())
                .build();

        when(transactionService.createTransaction(eq(userId), any(TransactionDTO.class))).thenReturn(dto);

        // When & Then
        mockMvc.perform(post(API_PREFIX)
                        .header("X-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.category").value("Coffee"));
    }

    @Test
    void shouldReturnSummary() throws Exception {
        // Given
        String userId = "user-123";
        DashboardSummaryDTO summary = DashboardSummaryDTO.builder()
                .totalIncome(new BigDecimal("1000"))
                .totalExpenses(new BigDecimal("200"))
                .totalBalance(new BigDecimal("800"))
                .expenseByCategory(Collections.emptyMap())
                .build();

        when(transactionService.getSummary(userId)).thenReturn(summary);

        // When & Then
        mockMvc.perform(get(API_PREFIX + "/summary")
                        .header("X-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncome").value(1000))
                .andExpect(jsonPath("$.totalExpenses").value(200));
    }
}
