package com.example.authservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Login {
    private String userId;
    private String username;
    private String password;
    private boolean active;
    private List<Role> roles;
}
