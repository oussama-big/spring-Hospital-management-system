package com.example.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SecretaryRegistrationRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;
import com.example.demo.exceptions.BusinessConflictException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.DoctorRepo;
import com.example.demo.repositories.PatientRepo;
import com.example.demo.repositories.RoleRepo;
import com.example.demo.repositories.UserRepo;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final RoleRepo roleRepo;






    @Transactional
    public User createUser( String firstName , String lastName , String email , String password , UserRole roleEnum){

        Role role = roleRepo.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Error: Role " + roleEnum + " not found."));
        
        var user = User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(passwordEncoder.encode(password))
            .role(role)
            .enabled(true)
            .build();
        return userRepo.save(user);
    }



    @Transactional
    public void signUp(SignUpRequest request){

        var user = userRepo.findByEmail(request.getEmail()).orElse(null);

        // if(userRepo.existsByEmail(request.getEmail() )){ 
        //     throw  new RuntimeException("User already exists");


         
        if(user==null){
                createUser(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword(),
                    UserRole.ROLE_PATIENT
                );
                //userRepo.save(user1);
        }else{
            throw new BusinessConflictException("User already exists");
        }


    }


    @Transactional
    public UserProfileResponse getCurrentUserProfile(Long userId) throws UserNotFoundException {

        var user = userRepo.findById(userId).orElseThrow(() ->{
         throw new UserNotFoundException();
    });

    UserProfileResponse response = userMapper.toResponse(user);
    UserRole roleEnum = user.getRole().getName(); // Your Enum

    // Shared ID Logic
    if (roleEnum == UserRole.ROLE_DOCTOR) {
        doctorRepo.findById(userId).ifPresent(doc -> response.setSpecialization(doc.getSpecialization()));
    } else if (roleEnum == UserRole.ROLE_PATIENT) {
        patientRepo.findById(userId).ifPresent(pat -> response.setBloodType(pat.getBloodType()));
    } else if (roleEnum == UserRole.ROLE_ADMIN) {
        // Admins don't need extra table lookups
        response.setNote("System Administrator - Full Access");
    }else{
        response.setNote("SECRETARY - Full Access");
    }

    return response;


    }


    public void registerSecretary( SecretaryRegistrationRequest request ) throws BusinessConflictException{

        if (userRepo.existsByEmail(request.getEmail())) {
        throw new BusinessConflictException("Email already in use");
    }

    createUser(request.getFirstName(),
                         request.getLastName(),
                         request.getEmail(),
                         request.getPassword(),
                         UserRole.ROLE_SECRETARY
                        );



    }



}