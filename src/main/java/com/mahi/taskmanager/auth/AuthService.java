package com.mahi.taskmanager.auth;

import com.mahi.taskmanager.common.DuplicateEmailException;
import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.AppUserRepository;
import com.mahi.taskmanager.user.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService (AppUserRepository appUserRepository, PasswordEncoder passwordEncoder){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
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

}
