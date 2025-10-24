package com.divysoni.productivityHub.services;


import com.divysoni.productivityHub.entities.taskManager.Task;
import org.bson.types.ObjectId;

import java.util.List;

public interface TaskService {
    List<Task> getTasks(ObjectId taskListId);
    Task createTask(ObjectId taskListId, Task task);
    Task getTaskById(ObjectId taskListId, ObjectId taskId);
    Task updateTask(ObjectId taskListId, ObjectId taskId, Task newTask);
    void deleteTask(ObjectId taskListId, ObjectId taskId);
    Task updateTagsOfTask(ObjectId taskListId, ObjectId taskId, String Tags);
}
