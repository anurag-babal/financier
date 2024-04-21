package com.example.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "User",
        description = "Schema to represent the user response"
)
public record UserResponseDto(
        @Schema(
                name = "id",
                description = "The unique identifier of the user",
                example = "1"
        )
        Long id,
        @Schema(
                name = "name",
                description = "The name of the user",
                example = "John Doe"
        )
        String name,
        @Schema(
                name = "email",
                description = "The email of the user",
                example = "abc@example.com"
        )
        String email,
        @Schema(
                name = "phone",
                description = "The phone number of the user",
                example = "+1234567890"
        )
        String phone,
        @Schema(
                name = "createdAt",
                description = "The timestamp when the user was created",
                example = "2021-12-31T23:59:59"
        )
        String createdAt,
        @Schema(
                name = "updatedAt",
                description = "The timestamp when the user was last updated",
                example = "2021-12-31T23:59:59"
        )
        String updatedAt
) {
}
