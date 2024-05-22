package com.example.userservice.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_details")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Login ID is mandatory")
    @Column(nullable = false, unique = true)
    private String loginId;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}
