package com.example.userservice.domain.repositories;

import com.example.userservice.data.dao.UserDao;
import com.example.userservice.data.entities.UserEntity;
import com.example.userservice.domain.model.User;
import com.example.userservice.dto.UserDetailsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final UserDao userDao;

    @Override
    public User addUser(User user) {
        UserEntity savedUser = userDao.save(mapToEntityUser(user));
        return mapToUser(savedUser);
    }

    private UserEntity mapToEntityUser(User user) {
        UserEntity userEnt;

        userEnt = userDao.findById(user.getId()).orElse(null);
        if (userEnt == null) {
            userEnt = new UserEntity();
        }

        userEnt.setId(user.getId());
        userEnt.setFirstName(user.getFirstName());
        userEnt.setMiddleName(user.getMiddleName());
        userEnt.setLastName(user.getLastName());
        userEnt.setEmail(user.getEmail());
        userEnt.setPhoneNumber(user.getPhoneNumber());
        userEnt.setDateOfBirth(user.getDateOfBirth());

        return userEnt;
    }

    private User mapToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setMiddleName(userEntity.getMiddleName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setDateOfBirth(userEntity.getDateOfBirth());
        return user;
    }

    public User getUser(int id) {
        UserEntity userEnt = userDao.findById(id).orElseThrow(null);
        return mapToUser(userEnt);
    }

}
