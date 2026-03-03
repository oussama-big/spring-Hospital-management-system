package com.example.demo.mappers;

import com.example.demo.dto.PatientResponse;
import com.example.demo.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    PatientResponse toResponse(Patient patient);
}
