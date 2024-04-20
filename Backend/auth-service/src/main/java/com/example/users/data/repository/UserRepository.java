package com.example.users.data.repository;

import com.example.users.data.dao.RoleEntityDao;
import com.example.users.data.dao.UserEntityDao;
import com.example.users.data.entity.UserEntity;
import com.example.users.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRepository {
    private final UserEntityDao userEntityDao;
    private final RoleEntityDao roleEntityDao;

    public User getUserById(Long id) {
        return User.builder().build();
    }

    public User getUserByEmail(String email) {
        return User.builder().build();
    }

    public User createUser(User user) {
        return User.builder().build();
    }

    public User updateUser(User user) {
        return User.builder().build();
    }

    public boolean deleteUser(Long id) {
        return true;
    }

    public boolean isEmailExists(String email) {
        return true;
    }

    public boolean isLoginExists(String username) {
        return true;
    }

    private User mapUserEntityToUserModel(UserEntity userEntity) {
        return User.builder()
                .name(userEntity.getFirstName() + " " + userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build();
    }
}
