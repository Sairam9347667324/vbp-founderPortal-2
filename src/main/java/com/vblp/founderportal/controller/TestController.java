package com.vblp.founderportal.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/secure")
    public String secureApi() {
        return "JWT is working!";
    }
}