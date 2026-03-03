package com.example.demo.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.demo.repositories.PatientRepo;
import com.example.demo.repositories.DoctorRepo;
import com.example.demo.repositories.AppointmentRepo;


@Service
@RequiredArgsConstructor
public class StatsService {

    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;


    public com.example.demo.dto.HospitalStatsResponse getHospitalStats() {
        return com.example.demo.dto.HospitalStatsResponse.builder()
                .totalPatients(patientRepo.count())
                .totalDoctors(doctorRepo.count())
                .totalAppointments(appointmentRepo.count())
                .doctorsPerDepartment(doctorRepo.countDoctorsByDepartment())
                .build();
    }


}
