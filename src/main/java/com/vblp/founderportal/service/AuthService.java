package com.vblp.founderportal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vblp.founderportal.entity.User;
import com.vblp.founderportal.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔥 REGISTER USER
    public String register(String username, String password) {
    	
    	username = username.trim().toLowerCase();
    	
        Optional<User> existingUser = userRepository.findByUsernameIgnoreCase(username);

        if (existingUser.isPresent()) {
            return "User already exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 🔐 encrypt password

        userRepository.save(user);

        return "User registered successfully";
    }

    // 🔥 LOGIN USER WITH DETAILED RESPONSE
    public String login(String username, String password) {
    	
    	username = username.trim().toLowerCase();

        Optional<User> userOpt = userRepository.findByUsernameIgnoreCase(username);

        if (userOpt.isEmpty()) {
            return "USER_NOT_FOUND";
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "PASSWORD_MISMATCH"; // 🔥 better name
        }

        return "SUCCESS";
    }

    // 🔥 OPTIONAL: Get user (for future use)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }
}