package com.example.userservice.domain.service;

import com.example.userservice.domain.model.User;
import com.example.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    public User updateUser(int id, User user) {
        return userRepository.updateUser(id, user);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public User getUserByLoginId(String loginId) {
        return userRepository.getUserByLoginId(loginId);
    }
}
