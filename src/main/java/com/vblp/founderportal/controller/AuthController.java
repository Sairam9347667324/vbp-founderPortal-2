package com.vblp.founderportal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vblp.founderportal.DTO.LoginRequest;
import com.vblp.founderportal.DTO.RegisterRequest;
import com.vblp.founderportal.config.JwtTokenService;
import com.vblp.founderportal.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthService authService;

    // 🔥 REGISTER API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        // 🔥 ADD HERE
    	String username = request.getUsername() != null ? request.getUsername().trim() : "";
    	String password = request.getPassword() != null ? request.getPassword().trim() : "";
    	String confirmPassword = request.getConfirmPassword() != null 
    	        ? request.getConfirmPassword().trim() : "";

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        String result = authService.register(username, password);

        if (result.equals("User already exists")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    // 🔥 LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 🔥 ADD HERE
    	String username = request.getUsername() != null ? request.getUsername().trim() : "";
    	String password = request.getPassword() != null ? request.getPassword().trim() : "";

        String result = authService.login(username, password);

        if (result.equals("USER_NOT_FOUND")) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (result.equals("PASSWORD_MISMATCH")) {
            return ResponseEntity.status(401).body("Password does not match");
        }

        String token = jwtTokenService.generateToken(username.toLowerCase());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}