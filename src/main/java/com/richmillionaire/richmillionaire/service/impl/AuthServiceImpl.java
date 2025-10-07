package com.richmillionaire.richmillionaire.service.impl;

import com.richmillionaire.richmillionaire.dao.RoleDao;
import com.richmillionaire.richmillionaire.dao.UserDao;
import com.richmillionaire.richmillionaire.dto.AuthRequest;
import com.richmillionaire.richmillionaire.dto.AuthResponse;
import com.richmillionaire.richmillionaire.dto.RegisterRequest;
import com.richmillionaire.richmillionaire.dto.UserDto;
import com.richmillionaire.richmillionaire.entity.RoleEntity;
import com.richmillionaire.richmillionaire.entity.UserEntity;
import com.richmillionaire.richmillionaire.service.AuthService;
import com.richmillionaire.richmillionaire.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userDao.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userDao.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Get default user role
        RoleEntity userRole = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        user = userDao.save(user);

        String jwtToken = jwtService.generateToken(user);
        UserDto userDto = convertToDto(user);

        return new AuthResponse(jwtToken, userDto);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserEntity user = userDao.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        UserDto userDto = convertToDto(user);

        return new AuthResponse(jwtToken, userDto);
    }

    @Override
    public UserDto getCurrentUser(String username) {
        UserEntity user = userDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToDto(user);
    }

    private UserDto convertToDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.getEnabled())
                .roles(user.getRoles().stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
