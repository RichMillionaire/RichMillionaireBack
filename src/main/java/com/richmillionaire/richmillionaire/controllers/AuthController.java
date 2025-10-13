package com.richmillionaire.richmillionaire.controllers;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.dto.ApiResponse;
import com.richmillionaire.richmillionaire.dto.AuthResponse;
import com.richmillionaire.richmillionaire.dto.LoginRequest;
import com.richmillionaire.richmillionaire.dto.RegisterRequest;
import com.richmillionaire.richmillionaire.dto.UserDto;
import com.richmillionaire.richmillionaire.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("Utilisateur enregistré avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

  @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.login(request);

            ResponseCookie cookie = ResponseCookie.from("token", authResponse.getToken())
                    .httpOnly(true)
                    .secure(false) 
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 jours
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());

            UserDto userDto = new UserDto();
            userDto.setUsername(authResponse.getUsername());
            userDto.setEmail(authResponse.getEmail());
            userDto.setRoles(authResponse.getRoles());

            return ResponseEntity.ok(ApiResponse.success("Connexion réussie", userDto));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }


}
