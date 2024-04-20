package com.example.users.domain.model;

import lombok.Data;

@Data
public class Address {
    private String city;
    private String state;
    private String country;
    private String zip;
}
