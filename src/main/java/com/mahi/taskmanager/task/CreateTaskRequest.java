package com.mahi.taskmanager.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Valid
public record CreateTaskRequest(
        @NotBlank @Size(max=200) String title,
        @Size(max = 2000) String description,
        Status status,
        @NotNull Priority priority,
        LocalDate dueDate
) {}
