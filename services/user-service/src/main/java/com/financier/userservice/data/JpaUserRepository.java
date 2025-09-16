package com.financier.userservice.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financier.userservice.data.entity.UserEntity;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
