package com.mahi.taskmanager.auth;

import java.time.Instant;

public record AuthResponse(
        String token,
        Instant expiresAt
) {
}
