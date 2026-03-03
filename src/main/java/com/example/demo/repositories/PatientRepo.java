package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long>{
    boolean existsByUserId(Long userId);

    @Query("SELECT p FROM Patient p WHERE " +
       "LOWER(p.user.firstName) LIKE LOWER(concat('%', :q, '%')) OR " +
       "LOWER(p.user.lastName) LIKE LOWER(concat('%', :q, '%'))")
List<Patient> searchByFullName(@Param("q") String query);

}
