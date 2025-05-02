package com.axa.api_sinistre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour les requêtes POST sans token
            .headers(headers -> headers.frameOptions().disable()) // Autoriser H2 en iframe
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/sinistres/**").permitAll() // Autoriser API publique
                .requestMatchers("/h2-console/**").permitAll()     // Autoriser H2 console
                .anyRequest().authenticated()                      // Sécuriser le reste
            )
            .httpBasic(); // Pour tests simples (curl, Postman, etc.)

        return http.build();
    }
}
