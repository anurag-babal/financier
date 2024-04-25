package com.example.userservice.data.dao;

import com.example.userservice.data.entities.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceDao extends JpaRepository<FinanceEntity, Integer> {
}
