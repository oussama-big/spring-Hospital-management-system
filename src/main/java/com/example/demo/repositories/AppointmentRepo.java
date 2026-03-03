package com.example.demo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Appointment;



public interface AppointmentRepo extends JpaRepository<Appointment, Long>{

   @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
       "AND a.appointmentDate >= :startDate " +
       "AND a.appointmentDate <= :endDate " + 
        "AND a.status = 'SCHEDULED'")
    List<Appointment> findAppointmentsByDoctorIdAndDateRange(
        @Param("doctorId") Long doctorId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.department.id = :deptId AND a.status = 'SCHEDULED'")
    List<Appointment> findPendingInDepartment(@Param("deptId") Long deptId);


}
