package com.richmillionaire.richmillionaire.controller;

import com.richmillionaire.richmillionaire.dto.AuthRequest;
import com.richmillionaire.richmillionaire.dto.AuthResponse;
import com.richmillionaire.richmillionaire.dto.RegisterRequest;
import com.richmillionaire.richmillionaire.dto.UserDto;
import com.richmillionaire.richmillionaire.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "API de gestion d'authentification et d'inscription")
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.cookie-name:jwt-token}")
    private String cookieName;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    @PostMapping("/register")
    @Operation(summary = "Inscription d'un nouvel utilisateur", description = "Crée un nouveau compte utilisateur avec les informations fournies")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.register(request);

        // Créer un cookie HTTP-only avec le token JWT
        Cookie jwtCookie = createJwtCookie(authResponse.getMessage(), (int) (jwtExpiration / 1000));
        response.addCookie(jwtCookie);

        // Retourner seulement les infos utilisateur (pas le token)
        return ResponseEntity.ok(AuthResponse.builder()
                .message("Registration successful")
                .user(authResponse.getUser())
                .build());
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.authenticate(request);

        // Créer un cookie HTTP-only avec le token JWT
        Cookie jwtCookie = createJwtCookie(authResponse.getMessage(), (int) (jwtExpiration / 1000));
        response.addCookie(jwtCookie);

        // Retourner seulement les infos utilisateur (pas le token)
        return ResponseEntity.ok(AuthResponse.builder()
                .message("Login successful")
                .user(authResponse.getUser())
                .build());
    }

    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Déconnecte l'utilisateur en supprimant le cookie JWT")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Supprimer le cookie en créant un cookie expiré
        Cookie jwtCookie = createJwtCookie("", 0);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Profil utilisateur", description = "Récupère les informations de l'utilisateur connecté")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto user = authService.getCurrentUser(username);
        return ResponseEntity.ok(user);
    }

    private Cookie createJwtCookie(String token, int maxAge) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Mettre à true en production avec HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "Lax");
        return cookie;
    }
}
