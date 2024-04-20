package com.example.users.data.dao;

import com.example.users.data.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginEntityDao extends JpaRepository<LoginEntity, Long> {
    Optional<LoginEntity> findByUsername(String username);
}
