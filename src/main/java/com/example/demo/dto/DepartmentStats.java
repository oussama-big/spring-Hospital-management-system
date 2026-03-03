package com.example.demo.dto;

import lombok.Data;
@Data

public class DepartmentStats {

    private String departmentName;
    private Long count;


    // Constructeur
    public DepartmentStats(String departmentName, Long count ) {
        this.departmentName = departmentName;
        this.count = count;
    }

}
