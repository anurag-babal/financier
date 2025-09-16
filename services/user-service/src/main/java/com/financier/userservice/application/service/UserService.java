
package com.financier.userservice.application.service;

import com.financier.userservice.application.dto.LoginRequest;
import com.financier.userservice.application.dto.RegisterRequest;
import com.financier.userservice.application.dto.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
    String login(LoginRequest request);
    UserResponse getProfile(String email);
}
