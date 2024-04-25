package com.example.userservice.data.dao;

import com.example.userservice.data.entities.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinanceDao extends JpaRepository<FinanceEntity, Integer> {
    Optional<FinanceEntity> findByUserIdId(int userId);
}
