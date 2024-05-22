package com.example.authservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(
        name = "Authenticate",
        description = "Schema to represent the authentication request"
)
public record AuthenticationRequestDto(
        @Schema(
                name = "username",
                description = "The username of the user",
                example = "username"
        )
        @NotBlank(message = "Username is required")
        String username,

        @Schema(
                name = "password",
                description = "The password of the user",
                example = "password"
        )
        @NotEmpty(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @Schema(
                name = "rememberMe",
                description = "Flag to remember the user",
                example = "true"
        )
        boolean rememberMe
) {
}
