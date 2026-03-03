package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    
    // Infos provenant de l'entité Patient
    private Long id;
    private String bloodType;
    private LocalDate dateOfBirth;
    private String gender;
    private String emergencyContact; 

    // Infos provenant de l'entité User (via la jointure)
    private String firstName;
    private String lastName;
    private String email;
}