package com.richmillionaire.richmillionaire.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.dto.ApiResponse;
import com.richmillionaire.richmillionaire.dto.AuthResponse;
import com.richmillionaire.richmillionaire.dto.LoginRequest;
import com.richmillionaire.richmillionaire.dto.RegisterRequest;
import com.richmillionaire.richmillionaire.dto.UserDto;
import com.richmillionaire.richmillionaire.models.User;
import com.richmillionaire.richmillionaire.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok(ApiResponse.success("Utilisateur enregistré avec succès ✅", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.login(request);
            String token = authResponse.getToken();

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(false);
            cookie.setSecure(false); // true en prod HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 jours
            response.addCookie(cookie);

            UserDto userDto = UserDto.builder()
                    .id(authResponse.getId())
                    .username(authResponse.getUsername())
                    .email(authResponse.getEmail())
                    .role(authResponse.getRoles()) // Set<String>
                    .createdAt(authResponse.getCreatedAt())
                    .updatedAt(authResponse.getUpdatedAt())
                    .build();

            return ResponseEntity.ok(ApiResponse.success("Connexion réussie ✅", userDto));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur de connexion : " + e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getMe(HttpServletRequest request) {
        User user = (User) request.getAttribute("user"); // récupéré depuis JwtAuthenticationFilter

        if (user == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Utilisateur non authentifié"));
        }

        // mapper Set<Role> -> Set<String>
        Set<String> roles = user.getRoles().stream()
                .map(r -> r.getName())
                .collect(Collectors.toSet());

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(roles)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Utilisateur connecté ✅", userDto));
    }
}
