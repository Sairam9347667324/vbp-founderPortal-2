package com.vblp.founderportal.config;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        logger.info("➡️ Incoming Request: {} {}", request.getMethod(), request.getRequestURI());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("⚠️ Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            logger.info("🔑 Token received: {}", token);

            String username = jwtTokenService.extractUsername(token);
            logger.info("👤 Extracted Username: {}", username);

            boolean isValid = jwtTokenService.validateToken(token);
            logger.info("✅ Token Valid: {}", isValid);

            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null &&
                isValid) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                logger.info("🎉 Authentication successful for user: {}", username);

                // 🔥 ADD THIS DEBUG LINE RIGHT HERE
                System.out.println("AUTH OBJECT: " +
                        SecurityContextHolder.getContext().getAuthentication());
            } else {
                logger.warn("❌ Authentication skipped (invalid token or already authenticated)");
            }

        } catch (Exception e) {
            logger.error("🚫 JWT Processing Error: {}", e.getMessage());
            e.printStackTrace(); // 🔥 ALSO IMPORTANT FOR DEBUG
        }

        filterChain.doFilter(request, response);
    }
}