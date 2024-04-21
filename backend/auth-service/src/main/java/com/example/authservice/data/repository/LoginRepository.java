package com.example.authservice.data.repository;

import com.example.authservice.data.dao.LoginEntityDao;
import com.example.authservice.data.dao.RoleEntityDao;
import com.example.authservice.data.entity.LoginEntity;
import com.example.authservice.data.entity.RoleEntity;
import com.example.authservice.domain.model.Login;
import com.example.authservice.domain.model.Role;
import com.example.authservice.exception.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LoginRepository {

    private final LoginEntityDao loginEntityDao;
    private final RoleEntityDao roleEntityDao;

    public Login getLoginByUsername(String username) {
        return mapLoginEntityToLoginModel(getLoginEntityByUsername(username));
    }

    private LoginEntity getLoginEntityByUsername(String username) {
        return loginEntityDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    public Login saveLogin(Login login) {
        Optional<LoginEntity> loginEntity = loginEntityDao.findByUsername(login.getUsername());
        if (loginEntity.isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }
        return mapLoginEntityToLoginModel(
                loginEntityDao.save(mapLoginModelToLoginEntity(login))
        );
    }

    public String saveRole(String name) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_" + name.toUpperCase());
        return roleEntityDao.save(roleEntity).getName();
    }

    private LoginEntity mapLoginModelToLoginEntity(Login login) {
        return LoginEntity.builder()
                .username(login.getUsername())
                .password(login.getPassword())
                .active(login.isActive())
                .roles(mapRoleModelsToRoleEntities(login.getRoles()))
                .build();
    }

    private List<RoleEntity> mapRoleModelsToRoleEntities(List<Role> roles) {
        return roles.stream().map(this::mapRoleModelToRoleEntity).collect(Collectors.toList());
    }

    private RoleEntity mapRoleModelToRoleEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        Optional<RoleEntity> roleEntityOptional = roleEntityDao.findByName(role.name());
        if (roleEntityOptional.isPresent()) {
            return roleEntityOptional.get();
        }
        roleEntity.setName(role.name());
        return roleEntity;
    }

    private Login mapLoginEntityToLoginModel(LoginEntity loginEntity) {
        return Login.builder()
                .userId(loginEntity.getUserId())
                .username(loginEntity.getUsername())
                .password(loginEntity.getPassword())
                .active(loginEntity.isActive())
                .roles(mapRoleEntitiesToRoleModels(loginEntity.getRoles()))
                .build();
    }

    private Role mapRoleEntityToRoleModel(RoleEntity roleEntity) {
        return Role.valueOf(roleEntity.getName());
    }

    private List<Role> mapRoleEntitiesToRoleModels(List<RoleEntity> roleEntities) {
        return roleEntities.stream().map(this::mapRoleEntityToRoleModel).collect(Collectors.toList());
    }
}
