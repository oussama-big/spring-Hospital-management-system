package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private java.time.LocalDate dateOfBirth;
    private String gender;
    private String bloodType;
    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;


}
