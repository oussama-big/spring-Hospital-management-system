package com.example.demo.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalStatsResponse {
    private long totalPatients;
    private long totalDoctors;
    private long totalAppointments;
    private List<DepartmentStats> doctorsPerDepartment;

}
