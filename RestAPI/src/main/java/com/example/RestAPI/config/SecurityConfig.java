package com.example.RestAPI.config;

import com.example.RestAPI.Enumeration.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Accès en lecture pour les rôles USER, MANAGER et ADMIN
                .requestMatchers("/api/items").hasAnyRole("ADMIN", "USER", "MANAGER")
                // Accès en lecture et suppression pour le rôle MANAGER et ADMIN
                .requestMatchers("/api/items/delete","/api/items/update").hasAnyRole("ADMIN")
                .requestMatchers("/api/items/add").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated()
            .and()
            .httpBasic()  // Basic authentication
            .and()
            .csrf().disable();  // Disable CSRF for simplicity
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Encode passwords for in-memory users
        String encodedAdminPassword = passwordEncoder.encode("admin");
        String encodedUserPassword = passwordEncoder.encode("user");
        String encodedManagerPassword = passwordEncoder.encode("manager");

        // Log encoded passwords for debugging (remove in production)
        logger.info("Encoded admin pass: {}", encodedAdminPassword);
        logger.info("Encoded user pass: {}", encodedUserPassword);
        logger.info("Encoded manager pass: {}", encodedManagerPassword);

        // Create in-memory users
        UserDetails admin = User.builder()
            .username("admin")
            .password(encodedAdminPassword)
            .roles(Role.ADMIN.name())  // Use enum for roles
            .build();

        UserDetails user = User.builder()
            .username("user")
            .password(encodedUserPassword)
            .roles(Role.USER.name())  // Use enum for roles
            .build();

        UserDetails manager = User.builder()
            .username("manager")
            .password(encodedManagerPassword)
            .roles(Role.MANAGER.name())  // Use enum for roles
            .build();

        return new InMemoryUserDetailsManager(admin, user, manager);
    }
}
