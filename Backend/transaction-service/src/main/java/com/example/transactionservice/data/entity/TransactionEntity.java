package com.example.transactionservice.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Builder
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class TransactionEntity extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount must not be null")
    @Column(name = "amount", nullable = false, updatable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "From account must not be null")
    @Column(name = "from_account", nullable = false, updatable = false)
    private Long fromAccount;

    @NotNull(message = "To account must not be null")
    @Column(name = "to_account", nullable = false, updatable = false)
    private Long toAccount;

    @NotBlank(message = "Status must not be blank")
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED')")
    private String status;

    @NotNull(message = "Transaction date must not be null")
    @Column(name = "transaction_date", nullable = false, updatable = false)
    private LocalDateTime transactionDate;

    @NotBlank(message = "Transaction type must not be blank")
    @Column(name = "transaction_type", nullable = false, updatable = false, columnDefinition = "ENUM('CREDIT', 'DEBIT')")
    private String transactionType;

    @NotNull(message = "Category id must not be null")
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    private String description;
}
