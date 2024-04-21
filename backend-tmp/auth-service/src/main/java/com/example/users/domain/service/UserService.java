package com.example.users.domain.service;

import com.example.users.domain.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByUserId(String userId);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(Long id);
}
