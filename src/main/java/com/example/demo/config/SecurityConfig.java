package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.AuthTokenFilter;





@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailService;
    private final AuthTokenFilter authTokenFilter;

    public SecurityConfig(UserDetailsService userDetailService , AuthTokenFilter authTokenFilter) {
        this.userDetailService = userDetailService;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

    // @Bean
    // public AuthTokenFilter authenticationJwtTokenFilter() {
    // return new AuthTokenFilter();
    // }

//     @Bean
//     public AuthenticationProvider authenticationProvider(){
//     var provider = new DaoAuthenticationProvider();
//     provider.setPasswordEncoder(passwordEncoder());
//     provider.setUserDetailsService(userDetailService);
//     return provider;
// }

@Bean
public AuthenticationProvider authenticationProvider() {
    // On donne directement le userDetailService au constructeur
    var provider = new DaoAuthenticationProvider(userDetailService); 
    // On garde le reste pour le mot de passe
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {
    //etap 0 : stateless authentication
        // Configurer Spring Security pour ne pas utiliser de sessions
        // Cela signifie que l'API REST ne maintiendra pas d'état entre les requêtes, ce qui est idéal pour les API RESTful
        // Note : Dans une application réelle, vous voudrez probablement utiliser une forme d'authentification stateless comme JWT ou OAuth2 pour sécuriser votre API
    //etap 1: Désactiver la protection CSRF pour les API REST
        // Désactiver la protection CSRF pour les API REST
        // Cela permet aux clients de faire des requêtes POST, PUT, DELETE sans avoir à gérer les tokens CSRF
        // Note : Ne pas désactiver CSRF pour les applications web classiques, cela peut exposer à des attaques CSRF
    // etap 2: Configurer les règles d'autorisation
        // Permettre toutes les requêtes sans authentification
        // Cela signifie que n'importe qui peut accéder à toutes les ressources de l'API sans avoir besoin de s'authentifier
        // Note : Dans une application réelle, vous voudrez probablement restreindre l'accès à certaines ressources en fonction des rôles ou des permissions des utilisateurs
        // Construire la chaîne de filtres de sécurité
    // etap 3: Construire la chaîne de filtres de sécurité
        // Cela finalise la configuration de sécurité et permet à Spring Security de l'appliquer à l'application
        // Note : La méthode build() est appelée pour construire la chaîne de filtres de sécurité à partir de la configuration définie précédemment
        http
            .sessionManagement( c->
                c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // etap 0
            .csrf( AbstractHttpConfigurer::disable) // etap 1
            .authorizeHttpRequests( a-> a// etap 2
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(
                            "/auth/**",
                            "/v3/api-docs/**",        // Opens the JSON documentation
                            "/swagger-ui/**",         // Opens the HTML UI
                            "/swagger-ui.html"        // Fallback UI link
                        ).permitAll()
                // .requestMatchers("/stats/**").permitAll()
                // .requestMatchers("/patients/**").permitAll()
                // .requestMatchers("/doctors/**").permitAll()
                // .requestMatchers("/departments/**").permitAll()
            // .requestMatchers(HttpMethod.POST , "/auth/login" , "/error").permitAll()// juste la méthode POST de /users est publique pour permettre la création de compte
                .anyRequest().authenticated() // private 
            )
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class) // Ajouter le filtre d'authentification JWT avant le filtre d'authentification standard de Spring Security
            .exceptionHandling(c -> {
                                    c.authenticationEntryPoint(
                                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                                    c.accessDeniedHandler((request, response, accessDeniedException) -> {
                                        response.setStatus(HttpStatus.FORBIDDEN.value());
                                    });
            });


        return http.build(); // etap 3




    }

}
