package com.mahi.taskmanager.task;

import com.mahi.taskmanager.common.ForbiddenException;
import com.mahi.taskmanager.common.ResourceNotFoundException;
import com.mahi.taskmanager.user.AppUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task createTask(CreateTaskRequest dto, AppUser appUser){
        Task task = new Task();
        task.setAppUser(appUser);
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority());
        if (dto.status() != null) task.setStatus(dto.status());
        task.setDueDate(dto.dueDate());

        return taskRepository.save(task);
    }

    public Task getTask(Long id, AppUser currentUser ){
        Task task =  taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
        if (!task.getAppUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You do not have access to this task");
        }
        return task;
    }

    public List<Task> getTasksByOwner(AppUser currentUser){
        return taskRepository.findByAppUser(currentUser);
    }

    @Transactional
    public Task updateTask(Long id, UpdateTaskRequest dto, AppUser currentUser){
        Task task = getTask(id, currentUser);
        if (dto.status() != null) task.setStatus(dto.status());
        if (dto.title() != null) task.setTitle(dto.title());
        if (dto.description() != null) task.setDescription(dto.description());
        if (dto.priority() != null) task.setPriority(dto.priority());
        if (dto.dueDate() != null) task.setDueDate(dto.dueDate());
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id, AppUser currentUser){
        Task task = getTask(id, currentUser);   // reuse the ownership check
        taskRepository.delete(task);
    }
}
