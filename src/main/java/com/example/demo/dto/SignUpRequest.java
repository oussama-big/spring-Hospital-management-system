package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    private java.util.Set<String> roles;
}
