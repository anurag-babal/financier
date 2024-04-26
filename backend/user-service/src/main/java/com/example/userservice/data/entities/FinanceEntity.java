package com.example.userservice.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "finance_details")
public class FinanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @Column(name = "budget", nullable = false)
    private int budget;

    @Column(name = "savings", nullable = false)
    private int savings;
}
