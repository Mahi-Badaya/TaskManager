package com.mahi.taskmanager.task;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid CreateTaskRequest request, @RequestParam Long ownerId){
        TaskResponse taskResponse = TaskResponse.from(taskService.createTask(request, ownerId));
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id){
        TaskResponse taskResponse = TaskResponse.from(taskService.getTask(id));
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByOwner(@RequestParam Long ownerId){
        return ResponseEntity.ok(
                taskService.getTasksByOwner(ownerId).stream()
                        .map(TaskResponse::from)
                        .toList()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody @Valid UpdateTaskRequest request,@PathVariable Long id){
        TaskResponse taskResponse = TaskResponse.from(taskService.updateTask(id, request));
        return  ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
