package com.mahi.taskmanager.task;

import com.mahi.taskmanager.user.AppUser;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}

enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDate dueDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    public Long getId(){ return id;}

    public AppUser getAppUser() { return appUser;}
    public void setAppUser(AppUser user){ this.appUser = user;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description;}

    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}

    public Priority getPriority() {return priority;}
    public void setPriority(Priority priority) {this.priority = priority;}

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    public Task(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Task(){}
}


