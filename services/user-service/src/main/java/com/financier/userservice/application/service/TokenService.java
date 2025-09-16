package com.financier.userservice.application.service;

import com.financier.userservice.domain.model.User;

@FunctionalInterface
public interface TokenService {
    String generateToken(User user);
}
