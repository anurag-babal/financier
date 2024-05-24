package com.example.authservice.mapper;

import com.example.authservice.dto.request.RegisterUserRequestDto;
import com.example.authservice.dto.request.UserCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserCreateRequestDto mapToUserCreateRequest(
            RegisterUserRequestDto registerUserRequestDto
    ) {
        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto();
        userCreateRequestDto.setLoginId(registerUserRequestDto.getUsername());
        userCreateRequestDto.setFirstName(registerUserRequestDto.getFirstName());
        userCreateRequestDto.setMiddleName("");
        userCreateRequestDto.setLastName(registerUserRequestDto.getLastName());
        userCreateRequestDto.setPhoneNumber(registerUserRequestDto.getPhoneNumber());
        userCreateRequestDto.setEmail(registerUserRequestDto.getEmail());
        userCreateRequestDto.setDateOfBirth(registerUserRequestDto.getDateOfBirth());
        userCreateRequestDto.setBudget(registerUserRequestDto.getBudget() == null ? 0.0 : registerUserRequestDto.getBudget());
        return userCreateRequestDto;
    }
}
