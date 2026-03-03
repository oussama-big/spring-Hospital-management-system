package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.dto.SecretaryRegistrationRequest;
import com.example.demo.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/register-secretary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerSecretary(@Valid @RequestBody SecretaryRegistrationRequest request) {
        userService.registerSecretary(request);
        return ResponseEntity.ok("Secretary registered successfully");
    }


}
