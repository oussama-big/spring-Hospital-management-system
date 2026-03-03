package com.example.demo.exceptions;


public class DoctorNotFoundException extends ResourceNotFoundException {
    public DoctorNotFoundException(Long id) {
        super("doctor not found with id: " + id);
    }


}
