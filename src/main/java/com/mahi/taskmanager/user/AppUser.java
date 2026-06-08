package com.mahi.taskmanager.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    private String email;

    public AppUser(){}

    public AppUser(String name){ this.name = name;}

    public Long getId(){return id;}

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email){this.email = email;}
}
