package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.DoctorRegistrationRequest;
import com.example.demo.dto.DoctorResponse;
import com.example.demo.entities.Doctor;
import com.example.demo.exceptions.DepartmentNotFoundException;
import com.example.demo.mappers.DoctorMapper;
import com.example.demo.repositories.DepartmentRepo;
import com.example.demo.repositories.DoctorRepo;
import com.example.demo.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;



@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final UserService userService;
    private final DepartmentRepo departmentRepo;
    private final DoctorMapper doctorMapper;
    private final UserRepo userRepo;


    @Transactional
    public DoctorResponse registerDotor(DoctorRegistrationRequest request){

        var user = userRepo.findByEmail(request.getEmail()).orElseGet(()->{ 

            User newUser = userService.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.ROLE_DOCTOR
            );
            return userRepo.saveAndFlush(newUser);
    });

        var depaertement = departmentRepo.findById(request.getDepartmentId()).orElseThrow(
            ()-> new DepartmentNotFoundException(request.getDepartmentId())
        );

        var doctor = Doctor.builder()
            .user(user)
            .specialization(request.getSpecialization())
            .licenseNumber(request.getLicenseNumber())
            .department(depaertement)
            .build();

        return doctorMapper.toResponse(doctorRepo.save(doctor));
    }

    @Transactional
    public List<DoctorResponse> getDoctorsOrderedByDepartment() {
    return doctorRepo.findAllByOrderByDepartmentNameAsc()
            .stream()
            .map(doctorMapper::toResponse)
            .toList();
    }

    @Transactional
    public List<DoctorResponse> getDoctorsOrderedByDepartmentId( Long departmentId) {
        return doctorRepo.findByDepartmentId(departmentId)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }


    @Transactional
    public List<DoctorResponse> search(String query){
        if(query==null || query.trim().isEmpty()){
            return getDoctorsOrderedByDepartment();
        }

        return doctorRepo.findByName(query)
            .stream()
            .map(doctorMapper::toResponse)
            .toList();
}
}
