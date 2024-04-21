package com.example.userservice.data.dao;

import com.example.userservice.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Integer> {
}
