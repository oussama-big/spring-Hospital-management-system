package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import com.example.demo.services.StatsService;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.HospitalStatsResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsSrevice;

    @GetMapping("/overview")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HospitalStatsResponse> getHospitalStats() {
        return ResponseEntity.ok(statsSrevice.getHospitalStats());
    }


}
