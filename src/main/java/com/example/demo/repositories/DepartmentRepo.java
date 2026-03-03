package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long>{

    boolean existsByName(String name);

}
