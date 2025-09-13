package com.financier.userservice.service;

import com.financier.userservice.model.LoginRequest;
import com.financier.userservice.model.RegisterRequest;
import com.financier.userservice.model.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
    String login(LoginRequest request);
    UserResponse getProfile(String email);
}
