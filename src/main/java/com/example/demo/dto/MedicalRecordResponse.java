package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalRecordResponse {

    private Long id;
    private String diagnosis;
    private String treatmentPlan;
    private java.time.LocalDateTime createdAt;
    private String doctorName;
    private String doctorSpecialization ;
}