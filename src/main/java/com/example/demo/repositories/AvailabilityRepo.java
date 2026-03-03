package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.DoctorAvailability;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepo extends JpaRepository<DoctorAvailability, Long>{

    Optional< DoctorAvailability> findByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek);
    List<DoctorAvailability> findAllByDoctorId(Long doctorId);
}
