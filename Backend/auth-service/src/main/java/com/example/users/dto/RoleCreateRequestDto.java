package com.example.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleCreateRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
}
