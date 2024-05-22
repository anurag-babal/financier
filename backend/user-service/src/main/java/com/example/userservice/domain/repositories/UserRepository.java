package com.example.userservice.domain.repositories;

import com.example.userservice.domain.model.User;

public interface UserRepository {
    User addUser(User user);

    User getUser(int id);

    User updateUser(int id, User user);

    boolean deleteUser(int id);

    User getUserByLoginId(String loginId);
}
