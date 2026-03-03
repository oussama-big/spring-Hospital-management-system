package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data

public class DepartmentRequest {
    @NotBlank(message = "Le nom du département est obligatoire")
    String name;
    @NotBlank(message = "La description du département est obligatoire")
    private String description;
}
