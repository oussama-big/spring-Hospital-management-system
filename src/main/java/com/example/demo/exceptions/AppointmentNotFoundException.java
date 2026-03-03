package com.example.demo.exceptions;

public class AppointmentNotFoundException extends ResourceNotFoundException {
    public AppointmentNotFoundException(Long id) {
        super("appointment not found with id: " + id);
    }


}
