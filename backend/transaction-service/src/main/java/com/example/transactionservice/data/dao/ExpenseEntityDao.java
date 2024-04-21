package com.example.transactionservice.data.dao;

import com.example.transactionservice.data.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseEntityDao extends JpaRepository<ExpenseEntity, Long> {
}
