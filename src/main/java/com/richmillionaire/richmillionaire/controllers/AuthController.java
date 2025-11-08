package com.richmillionaire.richmillionaire.controllers;

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

import jakarta.servlet.http.Cookie;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.login(request);
            String token = authResponse.getToken();

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);       
            cookie.setSecure(false); // Pour dev local en HTTP, mettre à true en prod en HTTPS         
            cookie.setPath("/");           
            cookie.setMaxAge(7*24 * 60 * 60); 

            response.addCookie(cookie);

            UserDto userDto = new UserDto(
                authResponse.getId(),
                authResponse.getUsername(),
                authResponse.getEmail(),
                authResponse.getRoles(), authResponse.getFirstName(), authResponse.getLastName(), authResponse.getCreatedAt(),authResponse.getUpdatedAt()
                
            );

            return ResponseEntity.ok(
                    ApiResponse.success("Connexion réussie ✅", userDto)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur de connexion : " + e.getMessage()));
        }
}

}
