package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.repositories.UserRepo;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.exceptions.BusinessConflictException;
import com.example.demo.exceptions.PatientNotFoundException;
import com.example.demo.repositories.PatientRepo;
import com.example.demo.dto.MedicalRecordResponse;
import com.example.demo.repositories.MedicaleRecordeRepo;
import com.example.demo.entities.Patient;


import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.PatientRegistrationRequest;
import com.example.demo.dto.PatientResponse;
import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entities.UserRole;
import com.example.demo.entities.User;
import com.example.demo.mappers.UserMapper;

import com.example.demo.mappers.PatientMapper;
@Service
@RequiredArgsConstructor
public class PatientService {

    private final  UserRepo userRepo ;
    private final PatientRepo patientRepo ;
    private final UserService userService ;
    private final PatientMapper patientMapper ;
    private final MedicaleRecordeRepo medicalRecordeRepo ;
    private final UserMapper userMapper ;






    @Transactional
    public PatientResponse createPatient( PatientRegistrationRequest request){

        var user = userRepo.findByEmail(request.getEmail()).
        orElseGet(()->{
            var newpatient =  userService.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.ROLE_PATIENT
            );
            return userRepo.saveAndFlush(newpatient);
        });

        if (patientRepo.existsByUserId(user.getId())) {
        throw new BusinessConflictException("Cet utilisateur est déjà enregistré en tant que patient.");
    }

       Patient patient = Patient.builder()
        .user(user)
        .bloodType(request.getBloodType()) // <-- Vérifie cette ligne
        .dateOfBirth(request.getDateOfBirth())
        .gender(request.getGender())
        .emergencyContact(request.getEmergencyContact()) // <-- Et celle-ci
        .build();
        return patientMapper.toResponse(patientRepo.save(patient));
    }


    public List<PatientResponse> searche(String query){
        var patients = patientRepo.searchByFullName(query);
        if(patients.isEmpty()){
            throw new BusinessConflictException("Aucun patient trouvé");
        }
        return patients.stream().map(patientMapper::toResponse).toList();
    }

    public List<MedicalRecordResponse> getMedicalRecords(Long patientId ){

       if( !patientRepo.existsById(patientId)){
            throw new PatientNotFoundException(patientId);
       }

       var records = medicalRecordeRepo.findByPatientIdOrderByCreatedAtDesc(patientId);

       return records.stream().map(record -> MedicalRecordResponse.builder()
            .id(record.getId())
            .diagnosis(record.getDiagnosis())
            .treatmentPlan(record.getTreatmentPlan())
            .createdAt(record.getCreatedAt())
            .doctorName( "Dr. "+ record.getDoctor().getUser().getFirstName() + " " + record.getDoctor().getUser().getLastName())
            .doctorSpecialization(record.getDoctor().getDepartment().getName())
            .build()).toList();
    }


    public List<UserProfileResponse> searchPatients( String query){

        List<User> users = userRepo.searchByRoleAndQuery(UserRole.ROLE_PATIENT, query);

        return users.stream().map(userMapper::toResponse)
            .collect(Collectors.toList());
    }



}
