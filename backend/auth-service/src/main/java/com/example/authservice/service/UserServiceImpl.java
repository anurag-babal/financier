package com.example.authservice.service;

import com.example.authservice.data.repository.LoginRepository;
import com.example.authservice.data.repository.UserRepository;
import com.example.authservice.domain.model.User;
import com.example.authservice.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserByUserId(String userId) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }
}
