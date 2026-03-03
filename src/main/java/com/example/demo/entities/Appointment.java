package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "appointments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.Column(name = "appointment_date" , nullable = false)
    private java.time.LocalDateTime appointmentDate;

    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    @jakarta.persistence.Column(name = "status")
    private AppointmentStatus status;

    @jakarta.persistence.Column(name = "reason")
    private String reason;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "patient_id" , referencedColumnName="id" , nullable = false)
    private Patient patient;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "doctor_id" , referencedColumnName="id" ,nullable = false)
    private Doctor doctor;


}
