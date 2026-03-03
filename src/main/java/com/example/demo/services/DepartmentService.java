package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dto.DepartmentRequest;
import com.example.demo.entities.Department;
import com.example.demo.repositories.DepartmentRepo;
import com.example.demo.exceptions.BusinessConflictException;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import com.example.demo.repositories.DoctorRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo ;
    private final DoctorRepo doctorRepo ;

    @Transactional
    public Department saveDepartment(DepartmentRequest request){

        if(departmentRepo.existsByName(request.getName())){
            throw new BusinessConflictException("Department already exists");
        }
        var department = Department.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
        return departmentRepo.save(department);
    }

    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if (!departmentRepo.existsById(id)) {
            throw new BusinessConflictException("Department not found");
        }
        if(doctorRepo.existsByDepartmentId(id)){
            throw new BusinessConflictException("Department has doctors");
        }

        departmentRepo.deleteById(id);
    }



}
