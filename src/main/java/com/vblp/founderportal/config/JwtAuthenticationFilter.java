package com.vblp.founderportal.config;

import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // Step 2: Check Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                // Step 3: Validate token
                if (jwtTokenService.validateToken(token)) {

                    // Step 4: Check if already authenticated
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {

                        // Step 5: Extract username
                        String username = jwtTokenService.extractUsername(token);

                        // Step 6: Create Authentication object (NO roles)
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        Collections.emptyList()
                                );

                        // Step 7: Attach request details
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        // Step 8: Set authentication in context
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

            } catch (Exception e) {
                // Optional: log error (avoid breaking request flow)
                System.out.println("JWT Authentication Error: " + e.getMessage());
            }
        }

        // Step 9: Continue filter chain
        filterChain.doFilter(request, response);
    }
}
