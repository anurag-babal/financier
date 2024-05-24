package com.example.authservice.controller;

import com.example.authservice.domain.model.Login;
import com.example.authservice.domain.service.LoginService;
import com.example.authservice.dto.RoleCreateRequestDto;
import com.example.authservice.dto.request.AuthenticationRequestDto;
import com.example.authservice.dto.request.RegisterUserRequestDto;
import com.example.authservice.dto.request.UserCreateRequestDto;
import com.example.authservice.dto.response.AuthenticationResponseDto;
import com.example.authservice.dto.response.ErrorResponseDto;
import com.example.authservice.dto.response.ResponseDto;
import com.example.authservice.dto.response.UserDto;
import com.example.authservice.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Tag(name = "Login", description = "Login API")
@RestController
@RequestMapping(path = "/api/v1/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoginController {

    private final UserMapper userMapper;
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Login",
            description = "Authenticate user and return JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody AuthenticationRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Login login = loginService.getLoginByUsername(request.username());
        UserDto userDto = loginService.getUserDetails(login.getUserId());
        String token = loginService.generateToken(login);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(HttpStatus.OK.getReasonPhrase())
                        .message("User authenticated successfully")
                        .data(new AuthenticationResponseDto(token, userDto))
                        .timestamp(new Date().toString())
                        .build()
        );
    }

    @Operation(
            summary = "Register",
            description = "Register user and return JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto request) {
        UserCreateRequestDto userCreateRequestDto = userMapper.mapToUserCreateRequest(request);
        UserDto userDto = loginService.registerUserDetails(request.getUsername(), request.getPassword(), userCreateRequestDto);
        Login login = loginService.getLoginByUsername(request.getUsername());
        String token = loginService.generateToken(login);
        return ResponseEntity.ok(new AuthenticationResponseDto(token, userDto));
    }

    @PostMapping("/role")
    public ResponseEntity<String> createRole (@Valid @RequestBody RoleCreateRequestDto request) {
        return ResponseEntity.ok(loginService.createRole(request.getName()));
    }
}
