package com.example.demo.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component

public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${Security.jwtSecret}")
    private String jwtSecret;

    @Value("${Security.access-token-expiration-ms}")
    private int jwtExpirationMs ;

    // public String generateJwtToken(Authentication authentication) {
    //     UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    //     String username = userDetails.getUsername();
    //     Long userId = userDetails.getId();
    //     return io.jsonwebtoken.Jwts.builder()
    //             .setSubject(username)
    //             .claim(userId)
    //             .setIssuedAt(new java.util.Date())
    //             .setExpiration(new java.util.Date((new java.util.Date()).getTime() + jwtExpirationMs))
    //             .signWith(key(),io.jsonwebtoken.SignatureAlgorithm.HS512)
    //             .compact();
    // }

    public String generateJwtToken(Authentication authentication) {
    // 1. Cast to your custom implementation to access the ID
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    
    return io.jsonwebtoken.Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            // 2. Add the ID as a named claim
            .claim("id", userPrincipal.getId()) 
            .claim("role", userPrincipal.getAuthorities().iterator().next().getAuthority())
            .setIssuedAt(new java.util.Date())
            .setExpiration(new java.util.Date((new java.util.Date()).getTime() + jwtExpirationMs))
            .signWith(key(), io.jsonwebtoken.SignatureAlgorithm.HS512)
            .compact();
}

    public String generateTokenFromUsername(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Uses your YAML setting!
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
}

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                                .setSigningKey(key())
                                .build()
                                .parseClaimsJws(token)
                                .getBody()
                                .getSubject();
    }

    public Long getUserIdFromJwtToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("id", Long.class); // Extract the ID as a Long
}

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT invalide: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expiré: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT non supporté: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Chaîne JWT vide: {}", e.getMessage());
        }
        return false;
    }


}
