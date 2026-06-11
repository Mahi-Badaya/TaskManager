package com.mahi.taskmanager.auth;

import com.mahi.taskmanager.common.DuplicateEmailException;
import com.mahi.taskmanager.common.InvalidCredentialsException;
import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.AppUserRepository;
import com.mahi.taskmanager.user.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Transactional(readOnly = true)
@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService (AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AppUser register(RegisterRequest request){
        appUserRepository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new DuplicateEmailException("Email already exists: " + request.email());
                });
        AppUser user = new AppUser();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(Role.USER);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return appUserRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest loginRequest){
        AppUser user = appUserRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        Instant expiresAt = Instant.now().plusMillis(jwtService.getExpirationMs());
        return new AuthResponse(token, expiresAt);
    }
}
