package com.example.userservice.mapper;

import com.example.userservice.data.entities.UserEntity;
import com.example.userservice.domain.model.User;
import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserCreateResponseDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserUpdateRequestDto;
import com.example.userservice.util.DateFormatter;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToUser(UserCreateRequestDto userCreateRequestDto) {
        User user = new User();
        user.setLoginId(userCreateRequestDto.getLoginId());
        user.setFirstName(userCreateRequestDto.getFirstName());
        user.setMiddleName(userCreateRequestDto.getMiddleName());
        user.setLastName(userCreateRequestDto.getLastName());
        user.setPhoneNumber(userCreateRequestDto.getPhoneNumber());
        user.setEmail(userCreateRequestDto.getEmail());
        user.setDateOfBirth(DateFormatter.stringToLocalDate(userCreateRequestDto.getDateOfBirth()));
        return user;
    }

    public User mapUpdateRequestToUser(UserUpdateRequestDto userUpdateRequestDto) {
        User user = new User();
        user.setId(userUpdateRequestDto.getId());
        user.setLoginId(userUpdateRequestDto.getLoginId());
        user.setFirstName(userUpdateRequestDto.getFirstName());
        user.setMiddleName(userUpdateRequestDto.getMiddleName());
        user.setLastName(userUpdateRequestDto.getLastName());
        user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
        user.setEmail(userUpdateRequestDto.getEmail());
        user.setDateOfBirth(DateFormatter.stringToLocalDate(userUpdateRequestDto.getDateOfBirth()));
        return user;
    }

    public UserCreateResponseDto mapToUserCreateResponseDto(User user) {
        UserCreateResponseDto userCreateResponseDto = new UserCreateResponseDto();
        userCreateResponseDto.setId(user.getId());
        userCreateResponseDto.setLoginId(user.getLoginId());
        userCreateResponseDto.setFirstName(user.getFirstName());
        userCreateResponseDto.setMiddleName(user.getMiddleName());
        userCreateResponseDto.setLastName(user.getLastName());
        userCreateResponseDto.setPhoneNumber(user.getPhoneNumber());
        userCreateResponseDto.setEmail(user.getEmail());
        userCreateResponseDto.setDateOfBirth(DateFormatter.localDateToString(user.getDateOfBirth()));
        return userCreateResponseDto;
    }

    public UserDetailsResponseDto mapToUserDetailsResponseDto(User user) {
        UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();
        userDetailsResponseDto.setLoginId(user.getLoginId());
        userDetailsResponseDto.setId(user.getId());
        userDetailsResponseDto.setFirstName(user.getFirstName());
        userDetailsResponseDto.setMiddleName(user.getMiddleName());
        userDetailsResponseDto.setLastName(user.getLastName());
        userDetailsResponseDto.setEmail(user.getEmail());
        userDetailsResponseDto.setPhoneNumber(user.getPhoneNumber());
        userDetailsResponseDto.setDateOfBirth(user.getDateOfBirth());
        return userDetailsResponseDto;
    }
}
