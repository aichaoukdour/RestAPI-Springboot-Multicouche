package com.example.RestAPI.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.Customizer.withDefaults;

@Component
public class SecurityConfigChain {

    public void configureRequests(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.GET, "/api/v1/items/{userId}/**")
                        .hasAnyRole("USER", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/items/{userId}/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/items/{userId}/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/items/{userId}/**").hasRole("USER")

                        .requestMatchers(HttpMethod.GET, "/api/v2/users/**").hasAnyRole("ADMIN", "MANAGER")                                                    // users
                        .requestMatchers(HttpMethod.POST, "/api/v2/users/**").hasRole("ADMIN") 
                        .requestMatchers(HttpMethod.PUT, "/api/v2/users/**").hasRole("ADMIN") 
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/users/**").hasRole("ADMIN") 

                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable());
    }

}