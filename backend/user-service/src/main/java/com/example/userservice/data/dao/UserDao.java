package com.example.userservice.data.dao;

import com.example.userservice.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByLoginId(String loginId);
}
