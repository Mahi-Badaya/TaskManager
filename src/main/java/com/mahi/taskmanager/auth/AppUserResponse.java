package com.mahi.taskmanager.auth;

import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.Role;

public record AppUserResponse(
        Long id,
        String name,
        String email,
        Role role
) {
    public static AppUserResponse from(AppUser user) {
        return new AppUserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}