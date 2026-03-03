package com.example.demo.mappers;

import com.example.demo.dto.AppointmentResponse;
import com.example.demo.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "patientName", expression = "java(appointment.getPatient().getUser().getFirstName() + \" \" + appointment.getPatient().getUser().getLastName())")
    @Mapping(target = "doctorName", expression = "java(\"Dr. \" + appointment.getDoctor().getUser().getLastName())")
    AppointmentResponse toResponse(Appointment appointment);
}