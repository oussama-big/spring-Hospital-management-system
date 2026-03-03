package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class DoctorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private String licenseNumber;
    private String departmentName; // On affiche le nom, pas l'ID

}
