package com.example.demo.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username; 
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Standard build from Database Entity
    public static UserDetailsImpl build(User user) {
        // Convert single role to a GrantedAuthority
        // Note: Adding "ROLE_" prefix is standard for Spring Security hasRole() checks
        String roleName = user.getRole().getName().name();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleName);

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority) // Wrap single item in a list
        );
    }

    // Overloaded build for JWT Filter (No password needed)
    public static UserDetailsImpl build(Long id, String username , String role ) {


        List <GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(role)
        );
        return new UserDetailsImpl(id, username, null, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}