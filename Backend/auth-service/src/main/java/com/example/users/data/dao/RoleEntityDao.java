package com.example.users.data.dao;

import com.example.users.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleEntityDao extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
