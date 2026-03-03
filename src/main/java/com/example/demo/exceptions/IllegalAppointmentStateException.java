package com.example.demo.exceptions;

public class IllegalAppointmentStateException extends BusinessConflictException {

    public IllegalAppointmentStateException(String message) {
        super(message);
    }
}
