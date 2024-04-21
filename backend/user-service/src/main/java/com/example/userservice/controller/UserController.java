package com.example.userservice.controller;

import com.example.userservice.domain.model.User;
import com.example.userservice.domain.service.UserService;
import com.example.userservice.dto.ResponseDto;
import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserCreateResponseDto;
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
                .status(HttpStatus.OK.value())
                .message("User added successfully")
                .data(userCreateResponseDto)
                .build();

        return ResponseEntity.ok().body(responseDto);
    }
}
