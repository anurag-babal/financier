package com.example.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Schema to represent the successful response of the API"
)
@Builder
@Data
public class ResponseDto {
        @Schema(
                name = "status",
                description = "The status of the response",
                example = "200"
        ) String status;

        @Schema(
                name = "message",
                description = "The message of the response",
                example = "User created successfully"
        )
        String message;

        @Schema(
                name = "data",
                description = "The data of the response",
                example = "{\"name\":\"John Doe\",\"email\":\"}"
        )
        Object data;

        @Schema(
                name = "timestamp",
                description = "The timestamp of the response",
                example = "2021-12-31T23:59:59"
        )
        String timestamp;
}
