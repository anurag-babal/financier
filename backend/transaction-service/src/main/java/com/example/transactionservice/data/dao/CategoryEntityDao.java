package com.example.transactionservice.data.dao;

import com.example.transactionservice.data.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryEntityDao extends JpaRepository<CategoryEntity, Long> {
}
