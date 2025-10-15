package com.divysoni.taskManager.services;


import com.divysoni.taskManager.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> getTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTaskById(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId, Task newTask);
    void deleteTask(UUID taskListId, UUID taskId);
}
