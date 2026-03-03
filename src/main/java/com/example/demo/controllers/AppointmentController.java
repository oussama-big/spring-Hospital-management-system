package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.dto.CompleteAppointmentRequest;
import com.example.demo.dto.AppointmentResponse;
import com.example.demo.services.AppointmentService;
import com.example.demo.jwt.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('SECRETARY', 'ADMIN')")
    public ResponseEntity<String> createAppointment(
        @RequestBody @Valid AppointmentRequest request
    ){
        appointmentService.createAppointment(request.getPatientId(), request.getDoctorId(), request.getAppointmentDate());
        return ResponseEntity.ok("appointment created");

    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> completeAppointment(
        @PathVariable Long id,
        @RequestBody @Valid CompleteAppointmentRequest request
    ){
        appointmentService.completeAppointment( id, request.getDiagnosis(), request.getTreatmentPlan());
        return ResponseEntity.ok("appointment completed");

    }
    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(
        @PathVariable Long id
    ){
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("appointment cancelled");

    }


    @GetMapping("/doctor/{doctorId}/today")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentResponse>> getDoctorDayAppointments(
        @PathVariable Long doctorId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        var appointments = appointmentService.getDectorDayAppontments(doctorId , userDetails.getId());
        return ResponseEntity.ok(appointments);
    }

}
