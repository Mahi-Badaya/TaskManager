package com.mahi.taskmanager.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        Long ownerId,
        String title,
        String description,
        Status status,
        Priority priority,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getAppUser().getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
