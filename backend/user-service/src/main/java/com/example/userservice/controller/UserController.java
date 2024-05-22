package com.example.userservice.controller;

import com.example.userservice.domain.model.User;
import com.example.userservice.domain.service.UserService;
import com.example.userservice.dto.*;
import com.example.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> addUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        User user = userMapper.mapToUser(userCreateRequestDto);
        user = userService.addUser(user);
        UserCreateResponseDto userCreateResponseDto = userMapper.mapToUserCreateResponseDto(user);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED,
                        "User Created Successfully",
                        userCreateResponseDto
                ), HttpStatus.CREATED
        );
    }

    @GetMapping("/{loginId}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable String loginId) {
        User user = userService.getUserByLoginId(loginId);
        UserDetailsResponseDto response = userMapper.mapToUserDetailsResponseDto(user);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "User Fetched Successfully",
                        response
                ), HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(
            @PathVariable int id,
            @RequestBody UserUpdateRequestDto userUpdateRequestDto
    ) {
        User user = userService.updateUser(id, userMapper.mapUpdateRequestToUser(userUpdateRequestDto));
        UserCreateResponseDto response = userMapper.mapToUserCreateResponseDto(user);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "User Updated Successfully",
                        response
                ), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "User Deleted Successfully",
                        deleted
                ), HttpStatus.OK
        );
    }
}
