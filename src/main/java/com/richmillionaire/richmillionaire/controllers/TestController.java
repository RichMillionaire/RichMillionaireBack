package com.richmillionaire.richmillionaire.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/public")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> publicTest() {
        return ResponseEntity.ok("Ceci est une route publique 🚀");
    }

    @GetMapping("/private")
    public ResponseEntity<String> privateTest(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return ResponseEntity.ok("Route privée accessible ✅ - User: " + username);
    }
}
