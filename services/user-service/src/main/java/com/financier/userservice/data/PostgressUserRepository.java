package com.financier.userservice.data;

import com.financier.userservice.domain.model.User;
import com.financier.userservice.domain.repository.UserRepository;
import com.financier.userservice.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgressUserRepository implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public PostgressUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return UserMapper.toDomain(saved);
    }
}
