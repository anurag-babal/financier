package com.example.userservice.domain.repositories;

import com.example.userservice.domain.model.User;

public interface UserRepository {
    public User addUser(User user);

    public User getUser(int id);

    public User updateUser(User user);

    public boolean deleteUser(int id);
}
