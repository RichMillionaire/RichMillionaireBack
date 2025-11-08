package com.richmillionaire.richmillionaire.middleware;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richmillionaire.richmillionaire.dto.ApiResponse;
import com.richmillionaire.richmillionaire.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Liste blanche = routes publiques
    private final List<String> publicEndpoints = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/test-public",
            "/swagger-ui",
            "/swagger"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (publicEndpoints.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            writeJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    ApiResponse.error("Aucun cookie trouvé"));
            return;
        }

        Cookie tokenCookie = Arrays.stream(cookies)
                .filter(c -> "token".equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (tokenCookie == null || tokenCookie.getValue().isBlank()) {
            writeJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    ApiResponse.error("UNAUTHORIZED: Aucun token trouvé"));
            return;
        }

        String token = tokenCookie.getValue();

        if (!jwtUtil.validateToken(token)) {
            writeJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    ApiResponse.error("Token invalide"));
            return;
        }

        request.setAttribute("username", jwtUtil.getUsername(token));

        filterChain.doFilter(request, response);
    }

    private void writeJsonResponse(HttpServletResponse response, int status, ApiResponse<?> apiResponse)
            throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
