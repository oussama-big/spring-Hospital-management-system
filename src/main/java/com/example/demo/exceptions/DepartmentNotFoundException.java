package com.example.demo.exceptions;

public class DepartmentNotFoundException extends ResourceNotFoundException {
    public DepartmentNotFoundException(Long id){
        super("department not found with id: " + id);
    }

}
