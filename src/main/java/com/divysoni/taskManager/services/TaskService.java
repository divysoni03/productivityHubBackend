package com.divysoni.taskManager.services;


import com.divysoni.taskManager.entities.taskManager.Task;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> getTasks(ObjectId taskListId);
    Task createTask(ObjectId taskListId, Task task);
    Task getTaskById(ObjectId taskListId, ObjectId taskId);
    Task updateTask(ObjectId taskListId, ObjectId taskId, Task newTask);
    void deleteTask(ObjectId taskListId, ObjectId taskId);
}
