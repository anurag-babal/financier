package com.financier.userservice.data;

import com.financier.userservice.data.entity.UserProfileEntity;
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

        UserProfileEntity profile = new UserProfileEntity();
        profile.setUser(entity);
        profile.setProfilePictureUrl(user.getProfilePictureUrl());
        profile.setBio(user.getBio());
        profile.setPhoneNumber(user.getPhoneNumber());
        profile.setCurrency(user.getCurrency());
        profile.setMonthlyBudget(user.getMonthlyBudget());

        entity.setProfile(profile);
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());

        UserProfileEntity profile = entity.getProfile();
        if (profile != null) {
            user.setProfilePictureUrl(profile.getProfilePictureUrl());
            user.setBio(profile.getBio());
            user.setPhoneNumber(profile.getPhoneNumber());
            user.setCurrency(profile.getCurrency());
            user.setMonthlyBudget(profile.getMonthlyBudget());
        }

        return user;
    }
}
