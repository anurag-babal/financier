package com.example.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "User",
        description = "Schema to represent the user creation request"
)
public record UserCreateDto(
        @Schema(
                name = "firstName",
                description = "The first name of the user",
                example = "John"
        )
        @NotEmpty(message = "First name is required")
        String firstName,
        @Schema(
                name = "lastName",
                description = "The last name of the user",
                example = "Doe"
        )
        @NotEmpty(message = "Last name is required")
        String lastName,
        @Schema(
                name = "email",
                description = "The email of the user",
                example = "abc@example.com"
        )
        @Email(message = "Email is required")
        String email,
        @Schema(
                name = "phone",
                description = "The phone number of the user",
                example = "+1234567890"
        )
        @NotEmpty(message = "Phone is required")
        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number")
        String phone
) {
}
