package com.example.demo.dto;

import com.example.demo.entities.AppointmentStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private String patientName;
    private String doctorName;
    private java.time.LocalDateTime appointmentDate;
    private AppointmentStatus status;
}
