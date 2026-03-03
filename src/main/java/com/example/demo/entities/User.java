package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import com.example.demo.entities.Role;



@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder // Super utile pour créer des objets facilement dans les tests
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "first_name" ,nullable = false)
    private String firstName;

    @Column(name= "last_name" , nullable= false)
    private String lastName;

    @Column( name= "email" ,nullable = false, unique = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id" , nullable = false)
    private Role role ;
}