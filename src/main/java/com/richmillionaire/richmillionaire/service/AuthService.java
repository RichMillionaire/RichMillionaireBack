package com.richmillionaire.richmillionaire.service;

import com.richmillionaire.richmillionaire.dto.AuthRequest;
import com.richmillionaire.richmillionaire.dto.AuthResponse;
import com.richmillionaire.richmillionaire.dto.RegisterRequest;
import com.richmillionaire.richmillionaire.dto.UserDto;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);

    UserDto getCurrentUser(String username);
}
