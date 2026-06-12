package com.mahi.taskmanager.auth;

import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.AppUserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppUserService {
    private final AppUserRepository repo;

    public AppUserService(AppUserRepository repo) { this.repo = repo; }

    public List<AppUser> getAllUsers() {
        return repo.findAll();
    }
}
