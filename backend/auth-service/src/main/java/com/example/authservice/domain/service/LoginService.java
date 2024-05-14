package com.example.authservice.domain.service;

import com.example.authservice.domain.model.Login;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {
    Login getLoginByUsername(String username);
    String generateToken(Login login);
    String registerUser(String username, String password);
    String createRole(String name);
}