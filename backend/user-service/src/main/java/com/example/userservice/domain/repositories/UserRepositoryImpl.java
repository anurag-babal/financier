package com.example.userservice.domain.repositories;

import com.example.userservice.data.dao.UserDao;
import com.example.userservice.data.entities.UserEntity;
import com.example.userservice.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final UserDao userDao;

    @Override
    public User addUser(User user) {
        UserEntity userEntity = mapToUserEntity(user);
        UserEntity savedUser = userDao.save(userEntity);
        return mapToUser(savedUser);
    }

    public User getUser(int id) {
        UserEntity userEnt = findUserEntityById(id);
        return mapToUser(userEnt);
    }

    public User getUserByLoginId(String loginId) {
        UserEntity userEntity = findUserEntityByLoginId(loginId);
        return mapToUser(userEntity);
    }

    private UserEntity findUserEntityByLoginId(String loginId) {
        return userDao
                .findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("User with loginId " + loginId + " not found"));
    }

    public User updateUser(int id, User user) {
        UserEntity userEnt = mapToUserEntity(user);
        userEnt.setId(id);
        UserEntity updatedUser = userDao.save(userEnt);
        return mapToUser(updatedUser);
    }

    public boolean deleteUser(int id) {
        UserEntity userEnt = findUserEntityById(id);
        userDao.delete(userEnt);
        return true;
    }

    private UserEntity findUserEntityById(int id) {
        return userDao.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    private UserEntity mapToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginId(user.getLoginId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setMiddleName(user.getMiddleName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setDateOfBirth(user.getDateOfBirth());
        userEntity.setBudget(BigDecimal.valueOf(user.getBudget()));

        return userEntity;
    }

    private User mapToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setLoginId(userEntity.getLoginId());
        user.setFirstName(userEntity.getFirstName());
        user.setMiddleName(userEntity.getMiddleName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setDateOfBirth(userEntity.getDateOfBirth());
        user.setBudget(userEntity.getBudget().doubleValue());

        return user;
    }

}
