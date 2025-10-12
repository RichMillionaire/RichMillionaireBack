package com.richmillionaire.richmillionaire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "Test", description = "Endpoints de test pour vérifier l'API")
public class TestController {

    @GetMapping("/public")
    @Operation(summary = "Endpoint public", description = "Endpoint accessible sans authentification")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/protected")
    @Operation(summary = "Endpoint protégé", description = "Endpoint nécessitant une authentification JWT")
    @SecurityRequirement(name = "bearerAuth")
    public String protectedEndpoint() {
        return "This is a protected endpoint - you are authenticated!";
    }
}
