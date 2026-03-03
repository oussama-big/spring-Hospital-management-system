package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class CompleteAppointmentRequest {
    @NotNull(message = "Diagnosis is required")
    private String diagnosis;
    @NotNull(message = "Treatment is required")
    private String treatmentPlan;
}
