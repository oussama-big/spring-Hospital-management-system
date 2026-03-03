package com.example.demo.services;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AppointmentResponse;
import com.example.demo.entities.Appointment;
import com.example.demo.entities.AppointmentStatus;
import com.example.demo.entities.DoctorAvailability;
import com.example.demo.entities.MedicaleRecorde;
import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.AppointmentConflictException;
import com.example.demo.exceptions.AppointmentNotFoundException;
import com.example.demo.exceptions.BusinessConflictException;
import com.example.demo.exceptions.DoctorNotFoundException;
import com.example.demo.exceptions.IllegalAppointmentStateException;
import com.example.demo.exceptions.PatientNotFoundException;
import com.example.demo.mappers.AppointmentMapper;
import com.example.demo.repositories.AppointmentRepo;
import com.example.demo.repositories.AvailabilityRepo;
import com.example.demo.repositories.DoctorRepo;
import com.example.demo.repositories.MedicaleRecordeRepo;
import com.example.demo.repositories.PatientRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class AppointmentService {

    private final PatientRepo patinRepo;
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final MedicaleRecordeRepo medicaleRecordeRepo;
    private final AppointmentMapper appointmentMapper;
    private final AvailabilityService availabilityService;
    private final AvailabilityRepo availabilityRepo;

    




    @Transactional
    public void createAppointment( Long patientId , Long doctorId , LocalDateTime appointmentDate){

        validateAgainstDoctorShift(  doctorId, appointmentDate);
        
        var patient =  patinRepo.findById(patientId).orElseThrow(()->
        new PatientNotFoundException(patientId)
        );
        var doctor = doctorRepo.findById(doctorId).orElseThrow(
            ()-> new DoctorNotFoundException(doctorId)
        );

        boolean isBusy = appointmentRepo.existsByDoctorIdAndAppointmentDate(doctorId, appointmentDate);
        if(isBusy){
            throw new AppointmentConflictException(appointmentDate);
        }
        var appointment = Appointment.builder()
            .patient(patient)
            .doctor(doctor)
            .appointmentDate(appointmentDate)
            .status(AppointmentStatus.SCHEDULED)
            .build();


        appointmentRepo.save(appointment);

    }
    private void validateAgainstDoctorShift(Long doctorId, LocalDateTime requestedDateTime) {
    DayOfWeek day = requestedDateTime.getDayOfWeek();
    LocalTime time = requestedDateTime.toLocalTime();

    // 1. Find the doctor's shift in the database
    DoctorAvailability shift = availabilityRepo.findByDoctorIdAndDayOfWeek(doctorId, day)
            .orElseThrow(() -> new BusinessConflictException("Le médecin ne travaille pas le " + day));

    // 2. Validate the time against the doctor's specific start/end times
    if (time.isBefore(shift.getStartTime()) || time.isAfter(shift.getEndTime())) {
        throw new BusinessConflictException("Le médecin est disponible uniquement de " + 
            shift.getStartTime() + " à " + shift.getEndTime());
    }
}
    // public void validatDate(LocalDateTime appointmentDate){
    //     DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
    //     int time = appointmentDate.getHour();
    //     if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
    //         throw new BusinessConflictException("L'hôpital est fermé le week-end. Veuillez choisir un jour entre lundi et vendredi.");
    //     }
    //     if(time < 8 || time >= 16 ){
    //         throw new BusinessConflictException("Les rendez-vous sont uniquement disponibles entre 08:00 et 16:00.");
    //     }
    // }

    @Transactional
    public void cancelAppointment(Long appointmentId){
        var appointment = appointmentRepo.findById(appointmentId).orElseThrow(
            ()-> new AppointmentNotFoundException(appointmentId)
        );
        if( appointment.getStatus().equals(AppointmentStatus.CANCELLED)){
            throw new IllegalAppointmentStateException("Ce rendez-vous est déjà annulé.");
        }
        if( appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
            throw new IllegalAppointmentStateException("Impossible d'annuler un rendez-vous déjà terminé.");
        }
        
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepo.save(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getDectorDayAppontments(Long doctorId , Long userId) {

       if (!doctorId.equals(userId)) {
        throw new AccessDeniedException("You can only view your own appointments.");
    }
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

       return appointmentRepo.findAppointmentsByDoctorIdAndDateRange(doctorId, start, end)
                                .stream()
                                .map(appointmentMapper::toResponse)
                                .toList();
    }

    @Transactional
    public void completeAppointment(Long appointmentId , String diagnosis , String treatmentPlan){
        var appointment = appointmentRepo.findById(appointmentId).orElseThrow( ()->
            new AppointmentNotFoundException(appointmentId)
        );
        if(appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
            throw new IllegalAppointmentStateException("Ce rendez-vous a déjà été clôturé.");
        }
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(appointment);


        var record = MedicaleRecorde.builder()
            .patient(appointment.getPatient())
            .doctor(appointment.getDoctor())
            .diagnosis(diagnosis)
            .treatmentPlan(treatmentPlan)
            .createdAt(LocalDateTime.now())
            .build();

        
            medicaleRecordeRepo.save(record);

    }



}
