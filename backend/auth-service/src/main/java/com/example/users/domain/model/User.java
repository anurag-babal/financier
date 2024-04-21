package com.example.users.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private String name;
    private String email;
    private String phone;
    private Address address;
}
