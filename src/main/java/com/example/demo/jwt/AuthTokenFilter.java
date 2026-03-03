package com.example.demo.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@Component

public class AuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Extraire le token du Header "Authorization"
            String jwt = parseJwt(request);

            // 2. Si le token existe et est valide
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                
                // 3. Récupérer l'identifiant (email) contenu dans le token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                Long userId = jwtUtils.getUserIdFromJwtToken(jwt);
                String role = jwtUtils.getRoleFromToken(jwt);


                // 4. Charger l'utilisateur depuis la base de données
                UserDetails userDetails = UserDetailsImpl.build( userId ,username ,role );

                // 5. Créer l'objet d'authentification officiel de Spring Security
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                // On ajoute les détails de la requête (IP, Session, etc.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Enregistrer l'utilisateur dans le "Contexte de Sécurité"
                // C'est ici que Spring "se rappelle" qui est connecté pour cette requête
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Impossible de définir l'authentification de l'utilisateur: {}", e);
        }

        // 7. Passer la main au filtre suivant (ou au Controller)
        filterChain.doFilter(request, response);
    }

    // Méthode utilitaire pour nettoyer le Header Authorization
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        // Un token JWT est toujours envoyé comme : "Bearer <token>"
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // On enlève "Bearer " (7 caractères) pour garder le code
        }

        return null;
    }
}
