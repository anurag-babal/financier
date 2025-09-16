package com.financier.userservice.infrastructure.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.financier.userservice.data.entity.UserEntity;
import com.financier.userservice.domain.model.User;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(UserEntity entity) {
        this.user = new User();
        this.user.setId(entity.getId());
        this.user.setUsername(entity.getUsername());
        this.user.setEmail(entity.getEmail());
        this.user.setPassword(entity.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    public User getUser() { return user; }
}
