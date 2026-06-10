package com.mahi.taskmanager.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private LocalDateTime createdAt;

    public AppUser(){}

    public AppUser(String name){ this.name = name;}

    public Long getId(){return id;}

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email){this.email = email;}

    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}

    public String getPasswordHash(){return passwordHash;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}

    @CreatedDate
    public LocalDateTime getCreatedAt(){return createdAt;}
}
