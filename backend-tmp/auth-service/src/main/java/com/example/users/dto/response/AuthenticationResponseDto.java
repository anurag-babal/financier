package com.example.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Authenticate",
        description = "Schema to represent the authentication response"
)
public record AuthenticationResponseDto(
        @Schema(
                name = "token",
                description = "The token for the user",
                example = "eyJhbGci"
        )
        String token
) {
}
