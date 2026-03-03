package com.example.demo.controllers;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DoctorRegistrationRequest;
import com.example.demo.dto.DoctorResponse;
import com.example.demo.services.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;


    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> registerDoctor(
        @Valid @RequestBody DoctorRegistrationRequest request
    ) {
        return new ResponseEntity<>(doctorService.registerDotor(request), HttpStatus.CREATED);
    }

    @GetMapping("/ordered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorResponse>> getOrdered() {
    return ResponseEntity.ok(doctorService.getDoctorsOrderedByDepartment());
    }

    @GetMapping("/department/doctors/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorResponse>> getOrderedByDepartmentId(
        @PathVariable Long departmentId){
            var doctors = doctorService.getDoctorsOrderedByDepartmentId(departmentId);
            return ResponseEntity.ok(doctors);
        }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorResponse>> searche(
        @RequestParam String query
    ){
        return ResponseEntity.ok(doctorService.search(query));
    }



}
