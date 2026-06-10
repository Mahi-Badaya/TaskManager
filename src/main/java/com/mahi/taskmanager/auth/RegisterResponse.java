package com.mahi.taskmanager.auth;

import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.Role;

import java.time.LocalDateTime;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        Role role,
        LocalDateTime createdAt
) {
    public static RegisterResponse from(AppUser appUser){
        return new RegisterResponse(
                appUser.getId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getRole(),
                appUser.getCreatedAt()
        );
    }
}
