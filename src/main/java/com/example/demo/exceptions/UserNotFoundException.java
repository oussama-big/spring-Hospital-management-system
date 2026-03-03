package com.example.demo.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException() {
        super("user not found with id: ");
    }


}
