package com.mahi.taskmanager.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

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
}
