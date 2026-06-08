package com.mahi.taskmanager.task;

import com.mahi.taskmanager.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByAppUserAndStatus(AppUser user, Status status);
    List<Task> findByDueDateBefore(LocalDate date);
    List<Task> findByAppUser(AppUser user);
}

