package com.example.authservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Authenticate",
        description = "Schema to represent the authentication response"
)
@Data
@AllArgsConstructor
public class AuthenticationResponseDto {
    @Schema(
            name = "token",
            description = "The token for the user",
            example = "eyJhbGci"
    )
    String token;
    UserDto user;
}
