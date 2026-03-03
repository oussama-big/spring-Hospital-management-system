package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.dto.DepartmentStats;


import com.example.demo.entities.Doctor;


public interface DoctorRepo extends JpaRepository<Doctor, Long>{

    List<Doctor> findAllByOrderByDepartmentIdAsc();

    List<Doctor> findAllByOrderByDepartmentNameAsc();

    // Dans le Repository
    List<Doctor> findByDepartmentId(Long departmentId);
    boolean existsByDepartmentId(Long departmentId);

    @Query("SELECT d FROM Doctor d WHERE "+
        "LOWER(d.user.firstName) LIKE LOWER(concat('%', :name, '%')) OR " +
        "LOWER(d.user.lastName) LIKE LOWER(concat('%', :name, '%')) " 
    )
    List<Doctor> findByName(@Param("name") String name);

    @Query("SELECT new com.example.demo.dto.DepartmentStats(d.department.name , COUNT(d)) " + 
        "FROM Doctor d " +
        "GROUP BY d.department.name"
    )
    List<DepartmentStats> countDoctorsByDepartment();


}
