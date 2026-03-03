package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.dto.PatientRegistrationRequest;
import com.example.demo.dto.PatientResponse;
import com.example.demo.services.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.jwt.UserDetailsImpl;


@RequiredArgsConstructor

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;



    @PostMapping("/register")
    public ResponseEntity<PatientResponse> registerPatient(@RequestBody @Valid PatientRegistrationRequest request) {
        return new ResponseEntity<>(patientService.createPatient(request) , HttpStatus.CREATED);
    }

    @GetMapping("/searche")
    public ResponseEntity<List<PatientResponse>> searche(
        @RequestParam String query
    ){
        return ResponseEntity.ok(patientService.searche(query));
    }

    @GetMapping("/medical-records")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<com.example.demo.dto.MedicalRecordResponse>> getMedicalRecords(
          @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok(patientService.getMedicalRecords(userDetails.getId()));
    }

    @GetMapping("/search-patients")
    @PreAuthorize("hasAnyRole('ADMIN' , 'SECRETARY' , 'DOCTOR')")
    public ResponseEntity<List<UserProfileResponse>> searchPatients( 
        @RequestParam String query
    ){
        return ResponseEntity.ok(patientService.searchPatients(query));
    } 

}
