package com.example.userservice.domain.repositories;

import com.example.userservice.domain.model.User;
import com.example.userservice.dto.UserDetailsResponseDto;

public interface UserRepository {
    public User addUser(User user);

    public User getUser(int id);

    public User updateUser(User user);

    public boolean deleteUser(int id);
}
