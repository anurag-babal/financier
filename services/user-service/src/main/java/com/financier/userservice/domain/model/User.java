package com.financier.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private String resetToken;
    private String refreshToken;
    private String profilePictureUrl;
    private String bio;
    private String phoneNumber;
    private String address;
    private String socialLinks; // JSON string containing social media links
    private String preferences; // JSON string for user preferences
    private String createdAt;
    private String updatedAt;
}
