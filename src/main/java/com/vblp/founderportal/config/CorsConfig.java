package com.vblp.founderportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")

                        // ✅ PRODUCTION DOMAIN
                        .allowedOrigins(
                                "https://crm.vblptechsolutions.com",

                                // ✅ LOCAL FRONTEND (Angular / React)
                                "http://localhost:4200",
                                "http://localhost:3000"
                        )
                        
                        .allowedOrigins("http://localhost:5173")
                       
                        // ✅ IF you really want wildcard ports → use this instead of above:
                        // .allowedOriginPatterns("http://localhost:*")

                        // ✅ HTTP METHODS
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // ✅ HEADERS
                        .allowedHeaders("*")

                        // ✅ IMPORTANT for JWT / Cookies / Authorization headers
                        .allowCredentials(true)

                        // ✅ CACHE PREFLIGHT (performance)
                        .maxAge(3600);
            }
        };
    }
}