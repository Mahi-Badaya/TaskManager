package com.mahi.taskmanager.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Valid
public record UpdateTaskRequest(
        @Size(max=200) String title,
        @Size(max = 2000) String description,
        Status status,
        Priority priority,
        LocalDate dueDate
) {}
