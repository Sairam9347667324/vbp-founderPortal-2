package com.vblp.founderportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")

                        // ✅ PRODUCTION: Allow ONLY your frontend domain
                        // Previously: .allowedOrigins("*")
                        .allowedOrigins("https://crm.vblptechsolutions.com",
                        "http://localhost:*")

                        // ✅ Allowed HTTP methods
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // ✅ Allow all headers from frontend
                        .allowedHeaders("*")

                        // ✅ Allow cookies / authorization headers if needed
                        .allowCredentials(true)

                        // ✅ Cache preflight request for 1 hour (performance improvement)
                        .maxAge(3600);
            }
        };
    }
}