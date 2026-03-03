package com.example.demo.repositories;
    
import java.util.Optional;

import com.example.demo.entities.Role;
import com.example.demo.entities.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}
