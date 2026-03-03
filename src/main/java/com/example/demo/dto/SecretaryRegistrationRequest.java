package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SecretaryRegistrationRequest {
    @NotBlank(message = "First name is required")
    @Size(max = 20)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 20)
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50)
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40)
    private String password;

}
