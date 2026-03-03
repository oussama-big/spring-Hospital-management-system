package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role; // DOCTOR, PATIENT, or ADMIN

    // Optional fields based on role
    private String specialization; // Only for Doctors
    private String bloodType;      // Only for Patients
    private String departmentName;
    private String note;
}
