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
    public ResponseDto addUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        User user = userService.addUser(userMapper.mapToUser(userCreateRequestDto));

        UserCreateResponseDto userCreateResponseDto = userMapper.mapToUserCreateResponseDto(user);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.CREATED)
                .message("User Added Successfully")
                .data(userCreateResponseDto)
                .build();

//        return ResponseEntity.ok(responseDto);
        return  responseDto;
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

    @PostMapping("/updateUser")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        User user = userService.updateUser(userMapper.mapUpdateRequestToUser(userUpdateRequestDto));
        UserCreateResponseDto response = userMapper.mapToUserCreateResponseDto(user);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("User Updated Successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("User Deleted Successfully")
                .data(deleted)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
