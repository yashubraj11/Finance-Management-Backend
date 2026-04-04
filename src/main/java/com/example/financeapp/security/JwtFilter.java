package com.example.financeapp.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth") || path.startsWith("/users");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        
        System.out.println("HEADER: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(7);

                
                System.out.println("TOKEN: " + token);

                Claims claims = jwtUtil.extract(token);

                String email = claims.getSubject();
                String role = (String) claims.get("role");

                
                System.out.println("EMAIL: " + email);
                System.out.println("ROLE: " + role);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                
                System.out.println("AUTHENTICATION SET SUCCESS");

            } catch (Exception e) {

                
                System.out.println("JWT ERROR: " + e.getMessage());

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        } else {
            
            System.out.println("NO TOKEN FOUND");
        }

        filterChain.doFilter(request, response);
    }
}