package com.vblp.founderportal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vblp.founderportal.DTO.LoginRequest;
import com.vblp.founderportal.config.JwtTokenService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // Simple Authentication (replace with DB later)
        if ("admin".equals(loginRequest.getUsername()) &&
            "Password".equals(loginRequest.getPassword())) {

            String token = jwtTokenService.generateToken(
                    loginRequest.getUsername()
            );

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response); // ✅ FIXED
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}