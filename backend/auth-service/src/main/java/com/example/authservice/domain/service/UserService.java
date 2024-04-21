package com.example.authservice.domain.service;

import com.example.authservice.domain.model.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByUserId(String userId);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(Long id);
}
