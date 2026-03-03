package com.example.demo.mappers;

import com.example.demo.dto.DoctorResponse;
import com.example.demo.entities.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface DoctorMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "department.name", target = "departmentName")
    DoctorResponse toResponse(Doctor doctor);

}
