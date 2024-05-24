package com.example.authservice.service.client;

import com.example.authservice.dto.request.UserCreateRequestDto;
import com.example.authservice.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping(value = "/api/v1/users")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserCreateRequestDto userCreateRequestDto);

    @GetMapping("/api/v1/users/{loginId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String loginId);
}
