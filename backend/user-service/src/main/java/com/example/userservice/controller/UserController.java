package com.example.userservice.controller;

import com.example.userservice.domain.model.User;
import com.example.userservice.domain.service.UserService;
import com.example.userservice.dto.*;
import com.example.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/")
    public ResponseEntity<ResponseDto> addUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        User user = userService.addUser(userMapper.mapToUser(userCreateRequestDto));

        UserCreateResponseDto userCreateResponseDto = userMapper.mapToUserCreateResponseDto(user);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.CREATED)
                .message("User added successfully")
                .data(userCreateResponseDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/getUser")
    public ResponseEntity<ResponseDto> getUser(@RequestBody UserDetailsRequestDto req) {
        User user = userService.getUser(req.getId());
        UserDetailsResponseDto response = userMapper.mapToUserDetailsResponseDto(user);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("Requested User")
                .data(response)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
