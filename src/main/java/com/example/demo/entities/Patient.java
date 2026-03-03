package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patients")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Patient {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_birth" , nullable = false)
    private java.time.LocalDate dateOfBirth;

    @Column(name = "gender" , nullable = false)
    private String gender;

    @Column(name = "blood_type" , nullable = false)
    private String bloodType;
    
    @Column(name = "emergency_contact" , nullable = false)
    private String emergencyContact;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "id" , nullable = false)
    private User user;
}
