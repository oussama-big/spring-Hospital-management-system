package com.example.demo.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AvailabilityRequest;
import com.example.demo.jwt.UserDetailsImpl;
import com.example.demo.services.AvailabilityService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/doctor/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/set")
    @PreAuthorize("hasRole('DOCTOR')") // Only doctors can set their own hours
    public ResponseEntity<String> setMyHours(@RequestBody AvailabilityRequest request, Authentication auth) {
        // Get the Shared ID of the authenticated doctor
        UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
        availabilityService.setAvailability( request,userPrincipal.getId());
        
        return ResponseEntity.ok("Schedule updated for " + request.getDayOfWeek());
    }
}
