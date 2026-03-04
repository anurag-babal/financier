package com.financier.userservice.data;

import com.financier.userservice.data.entity.UserEntity;
import com.financier.userservice.domain.model.User;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setProfilePictureUrl(user.getProfilePictureUrl());
        entity.setBio(user.getBio());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setCurrency(user.getCurrency());
        entity.setMonthlyBudget(user.getMonthlyBudget());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setProfilePictureUrl(entity.getProfilePictureUrl());
        user.setBio(entity.getBio());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setCurrency(entity.getCurrency());
        user.setMonthlyBudget(entity.getMonthlyBudget());
        return user;
    }
}
