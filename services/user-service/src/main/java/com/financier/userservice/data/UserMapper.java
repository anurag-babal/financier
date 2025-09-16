package com.financier.userservice.data;

import com.financier.userservice.data.entity.UserEntity;
import com.financier.userservice.domain.model.User;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        return user;
    }
}
