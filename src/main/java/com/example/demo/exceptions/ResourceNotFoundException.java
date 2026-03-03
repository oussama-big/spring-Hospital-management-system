package com.example.demo.exceptions;

public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
    
    }

}
