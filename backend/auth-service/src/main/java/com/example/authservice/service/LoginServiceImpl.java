package com.example.authservice.service;

import com.example.authservice.config.JwtUtil;
import com.example.authservice.data.repository.LoginRepository;
import com.example.authservice.domain.model.Login;
import com.example.authservice.domain.model.Role;
import com.example.authservice.domain.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Login getLoginByUsername(String username) {
        return loginRepository.getLoginByUsername(username);
    }

    @Override
    public String generateToken(Login login) {
        return jwtUtil.generateToken(login);
    }

    @Override
    public String registerUser(String username, String password) {
        Login login = Login.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .active(true)
                .roles(List.of(Role.ROLE_USER))
                .build();
        return generateToken(loginRepository.saveLogin(login));
    }

    @Override
    public String createRole(String name) {
        return loginRepository.saveRole(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = getLoginByUsername(username);
        return mapLoginToUserDetails(login);
    }

    private UserDetails mapLoginToUserDetails(Login login) {
        return new org.springframework.security.core.userdetails.User(
                login.getUsername(),
                login.getPassword(),
                login.isActive(),
                true,
                true,
                true,
                login.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())
        );
    }
}
