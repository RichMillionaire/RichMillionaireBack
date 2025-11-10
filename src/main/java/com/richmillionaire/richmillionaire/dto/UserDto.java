package com.richmillionaire.richmillionaire.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull
    private UUID id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private Set<String> role;

    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
