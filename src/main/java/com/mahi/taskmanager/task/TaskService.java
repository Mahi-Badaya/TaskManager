package com.mahi.taskmanager.task;

import com.mahi.taskmanager.common.ResourceNotFoundException;
import com.mahi.taskmanager.user.AppUser;
import com.mahi.taskmanager.user.AppUserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;

    public TaskService (TaskRepository taskRepository, AppUserRepository appUserRepository){
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public Task createTask(CreateTaskRequest dto, Long ownerId){
        AppUser owner = appUserRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + ownerId));

        Task task = new Task();
        task.setAppUser(owner);
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority());
        if (dto.status() != null) task.setStatus(dto.status());
        task.setDueDate(dto.dueDate());

        return taskRepository.save(task);
    }

    public Task getTask(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }

    public List<Task> getTasksByOwner(Long ownerId){
        AppUser owner = appUserRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + ownerId));

        return taskRepository.findByAppUser(owner);
    }

    @Transactional
    public Task updateTask(Long id, UpdateTaskRequest dto){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));

        if (dto.status() != null) task.setStatus(dto.status());
        if (dto.title() != null) task.setTitle(dto.title());
        if (dto.description() != null) task.setDescription(dto.description());
        if (dto.priority() != null) task.setPriority(dto.priority());
        if (dto.dueDate() != null) task.setDueDate(dto.dueDate());

        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
        taskRepository.delete(task);
    }
}
