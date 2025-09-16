package com.financier.userservice.domain.repository;

import java.util.Optional;

import com.financier.userservice.domain.model.User;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
}
