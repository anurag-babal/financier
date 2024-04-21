package com.example.userservice.mapper;

import com.example.userservice.domain.model.User;
import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserCreateResponseDto;
import com.example.userservice.util.DateFormatter;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToUser(UserCreateRequestDto userCreateRequestDto) {
        User user = new User();
        user.setFirstName(userCreateRequestDto.getFirstName());
        user.setMiddleName(userCreateRequestDto.getMiddleName());
        user.setLastName(userCreateRequestDto.getLastName());
        user.setPhoneNumber(userCreateRequestDto.getPhoneNumber());
        user.setEmail(userCreateRequestDto.getEmail());
        user.setDateOfBirth(DateFormatter.stringToLocalDate(userCreateRequestDto.getDateOfBirth()));
        return user;
    }

    public UserCreateResponseDto mapToUserCreateResponseDto(User user) {
        UserCreateResponseDto userCreateResponseDto = new UserCreateResponseDto();
        userCreateResponseDto.setId(user.getId());
        userCreateResponseDto.setFirstName(user.getFirstName());
        userCreateResponseDto.setMiddleName(user.getMiddleName());
        userCreateResponseDto.setLastName(user.getLastName());
        userCreateResponseDto.setPhoneNumber(user.getPhoneNumber());
        userCreateResponseDto.setEmail(user.getEmail());
        userCreateResponseDto.setDateOfBirth(DateFormatter.localDateToString(user.getDateOfBirth()));
        return userCreateResponseDto;
    }
}
