package com.mahi.taskmanager.task;

import com.mahi.taskmanager.user.AppUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid CreateTaskRequest request, @AuthenticationPrincipal AppUser currentUser){
        TaskResponse taskResponse = TaskResponse.from(taskService.createTask(request, currentUser));
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id, @AuthenticationPrincipal AppUser currentUser){
        TaskResponse taskResponse = TaskResponse.from(taskService.getTask(id, currentUser));
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByOwner(@AuthenticationPrincipal AppUser currentUser){
        return ResponseEntity.ok(
                taskService.getTasksByOwner(currentUser).stream()
                        .map(TaskResponse::from)
                        .toList()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody @Valid UpdateTaskRequest request,@PathVariable Long id, @AuthenticationPrincipal AppUser currentUser){
        TaskResponse taskResponse = TaskResponse.from(taskService.updateTask(id, request, currentUser));
        return  ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @AuthenticationPrincipal AppUser currentUser){
        taskService.deleteTask(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
