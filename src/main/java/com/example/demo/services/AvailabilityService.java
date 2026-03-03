package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AvailabilityRequest;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.DoctorAvailability;
import com.example.demo.repositories.AvailabilityRepo;
import com.example.demo.repositories.DoctorRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepo availabilityRepo;
    private final DoctorRepo doctorRepo;


    @Transactional
    public void setAvailability(AvailabilityRequest request , Long doctorId) {

    // 1. Verify the doctor exists in the specialized table
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 2. Check if a shift already exists for this day
        DoctorAvailability availability = availabilityRepo
                .findByDoctorIdAndDayOfWeek(doctorId, request.getDayOfWeek())
                .orElse(new DoctorAvailability());

        // 3. Update or Set details
        availability.setDoctor(doctor);
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());

        availabilityRepo.save(availability);


    }



}
