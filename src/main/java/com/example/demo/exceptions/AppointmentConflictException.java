package com.example.demo.exceptions;

import java.time.LocalDateTime;

public class AppointmentConflictException extends BusinessConflictException {

    public AppointmentConflictException(LocalDateTime date) {
        super("Le docteur est deje occupe à ce creneau : " + date);
    }
}
