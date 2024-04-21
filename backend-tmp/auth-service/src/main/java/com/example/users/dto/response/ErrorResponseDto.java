package com.example.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to represent the error response of the API"
)
public record ErrorResponseDto(
        @Schema(
                name = "api",
                description = "The API that was called",
                example = "/api/v1/users"
        )
        String apiPath,
        @Schema(
                name = "errorCode",
                description = "The error code of the response",
                example = "404"
        )
        HttpStatus errorCode,
        @Schema(
                name = "message",
                description = "Error message representing the error happened",
                example = "User not found"
        )
        String errorMessage,
        @Schema(
                name = "timestamp",
                description = "The timestamp of the response",
                example = "2021-12-31T23:59:59"
        )
        LocalDateTime timestamp
) {
}
