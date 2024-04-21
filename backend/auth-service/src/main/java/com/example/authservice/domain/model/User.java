package com.example.authservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String name;
    private String email;
    private String phone;
    private Address address;
}
