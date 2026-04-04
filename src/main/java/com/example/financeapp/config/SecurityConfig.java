package com.example.financeapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.financeapp.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth

           
            .requestMatchers("/auth/**", "/users/**").permitAll()
            .requestMatchers(
            	    "/v3/api-docs/**",
            	    "/swagger-ui/**",
            	    "/swagger-ui.html"
            	).permitAll()   
            
            .requestMatchers("/dashboard/**")
            .hasAnyRole("ADMIN", "ANALYST", "VIEWER")

            
            .requestMatchers(HttpMethod.GET, "/records/**")
            .hasAnyRole("ADMIN", "ANALYST")

           
            .requestMatchers(HttpMethod.POST, "/records/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/records/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/records/**").hasRole("ADMIN")

            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}