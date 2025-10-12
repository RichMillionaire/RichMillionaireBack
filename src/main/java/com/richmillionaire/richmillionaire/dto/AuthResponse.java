package com.richmillionaire.richmillionaire.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String message;
    private UserDto user;

    public AuthResponse(UserDto user) {
        this.message = "Authentication successful";
        this.user = user;
    }
}
