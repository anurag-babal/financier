package com.example.expenseservice.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ExpenseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User ID is required")
    @Column(nullable = false, updatable = false)
    private String userId;
    @Size(max = 255, message = "Description is too long")
    @Column(length = 255)
    private String description;
    @NotNull(message = "Amount is required")
    @Column(nullable = false)
    private BigDecimal amount;
    @NotNull(message = "Transaction date is required")
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;
}
