package com.example.authservice.domain.service;

import com.example.authservice.domain.model.Login;
import com.example.authservice.dto.request.UserCreateRequestDto;
import com.example.authservice.dto.response.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {
    Login getLoginByUsername(String username);

    String generateToken(Login login);

    Login registerUser(String username, String password);

    String createRole(String name);

    UserDto registerUserDetails(String username, String password, UserCreateRequestDto userCreateRequestDto);

    UserDto getUserDetails(String loginId);
}
