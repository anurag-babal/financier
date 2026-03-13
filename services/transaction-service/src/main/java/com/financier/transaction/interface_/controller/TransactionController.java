package com.financier.transaction.interface_.controller;

import com.financier.transaction.application.dto.DashboardSummaryDTO;
import com.financier.transaction.application.dto.TransactionDTO;
import com.financier.transaction.application.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.financier.transaction.TransactionServiceApplication.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.createTransaction(userId, transactionDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getUserTransactions(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<TransactionDTO>> getRecentTransactions(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(transactionService.getRecentTransactions(userId, limit));
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getSummary(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(transactionService.getSummary(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable UUID id) {
        TransactionDTO transaction = transactionService.getTransactionById(userId, id);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable UUID id,
            @Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO updated = transactionService.updateTransaction(userId, id, transactionDTO);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable UUID id) {
        TransactionDTO transaction = transactionService.getTransactionById(userId, id);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        transactionService.deleteTransaction(userId, id);
        return ResponseEntity.noContent().build();
    }
}
