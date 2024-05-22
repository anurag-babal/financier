package com.example.userservice.data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "finance_details")
public class FinanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private BigDecimal budget;

    @Column(nullable = false)
    private BigDecimal savings;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;
}
