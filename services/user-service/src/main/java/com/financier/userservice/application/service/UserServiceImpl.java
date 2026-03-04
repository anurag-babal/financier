package com.financier.userservice.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.financier.userservice.application.dto.LoginRequest;
import com.financier.userservice.application.dto.RegisterRequest;
import com.financier.userservice.application.dto.UserResponse;
import com.financier.userservice.domain.model.User;
import com.financier.userservice.domain.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Handle name and ensure username is not null for the database constraint
        String name = request.getName();
        String username = request.getUsername();
        if (username == null || username.isEmpty()) {
            username = request.getEmail(); // Fallback to email as username
        }
        if (name == null || name.isEmpty()) {
            name = username; // Fallback to username for name if both missing
        }

        User user = User.builder()
                .name(name)
                .username(username)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                .build();
        user = userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return tokenService.generateToken(user);
    }

    @Override
    public UserResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    @Override
    public UserResponse getProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    @Override
    public UserResponse updateProfile(Long id, UserResponse request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null)
            user.setName(request.getName());

        // Handle profile fields
        if (request.getBio() != null)
            user.setBio(request.getBio());
        if (request.getProfilePictureUrl() != null)
            user.setProfilePictureUrl(request.getProfilePictureUrl());
        if (request.getCurrency() != null)
            user.setCurrency(request.getCurrency());
        if (request.getMonthlyBudget() != null)
            user.setMonthlyBudget(request.getMonthlyBudget());

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setBio(user.getBio());
        response.setProfilePictureUrl(user.getProfilePictureUrl());
        response.setCurrency(user.getCurrency());
        response.setMonthlyBudget(user.getMonthlyBudget());
        return response;
    }
}
