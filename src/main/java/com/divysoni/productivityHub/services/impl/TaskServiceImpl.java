package com.divysoni.productivityHub.services.impl;

import com.divysoni.productivityHub.entities.taskManager.Task;
import com.divysoni.productivityHub.entities.taskManager.TaskList;
import com.divysoni.productivityHub.entities.taskManager.TaskPriority;
import com.divysoni.productivityHub.entities.taskManager.TaskStatus;
import com.divysoni.productivityHub.repo.taskManager.TaskRepo;
import com.divysoni.productivityHub.services.TaskService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final TaskListServiceImpl taskListService;

    public TaskServiceImpl(TaskRepo taskRepo, TaskListServiceImpl taskListService) {
        this.taskRepo=taskRepo;
        this.taskListService=taskListService;
    }

    @Override
    public List<Task> getTasks(ObjectId taskListId) {
        TaskList taskList = taskListService.getTaskList(taskListId);
        return Optional.ofNullable(taskList.getTasks())
                .orElse(Collections.emptyList());
    }


    @Transactional
    @Override
    public Task createTask(ObjectId taskListId, Task task) {
        if(null != task.getId()) {
            throw new IllegalArgumentException("Task already has an Id!");
        }
        if(task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.of(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskList taskList = taskListService.getTaskList(taskListId);
        if(taskList == null) throw new IllegalArgumentException("TaskList doesn't exists, enter valid Id!");

        LocalDateTime nowTime = LocalDateTime.now();
        task.setId(null);
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(taskPriority);
        task.setCreated(nowTime);
        task.setUpdated(nowTime);

        Task savedTask = taskRepo.save(task);

        taskList.getTasks().add(savedTask);
        taskListService.saveTaskList(taskList);

        return savedTask;
    }

    @Override
    public Task getTaskById(ObjectId taskListId, ObjectId taskId) {
        TaskList tasksLists = taskListService.getTaskList(taskListId);
        List<Task> collected = tasksLists.getTasks().stream().filter(tasks -> tasks.getId().equals(taskId)).toList();

        if(collected.isEmpty()) throw new IllegalArgumentException("Invalid Task Id, Try again!");

        return taskRepo.findById(taskId).orElseThrow(()-> new IllegalArgumentException("Invalid Task Id, Try again!"));

    }

    @Override
    public Task updateTask(ObjectId taskListId, ObjectId taskId, Task newTask) {
        if(null == newTask.getPriority()) {
            throw new IllegalArgumentException("Task must have a valid priority!");
        }
        if(null == newTask.getStatus()) {
            throw new IllegalArgumentException("Task must have a valid status!");
        }

        TaskList taskList = taskListService.getTaskList(taskListId);
        List<Task> collected = taskList.getTasks().stream().filter(task-> task.getId().equals(taskId)).toList();
        if(collected.isEmpty()) throw new IllegalArgumentException("Invalid Task Id!");
        Task oldTask = taskRepo.findById(taskId).orElseThrow(()->new IllegalArgumentException("Invalid Task Id!"));

        oldTask.setTitle(newTask.getTitle().isBlank()? oldTask.getTitle() : newTask.getTitle());
        oldTask.setDescription(newTask.getDescription() == null ? oldTask.getDescription() : newTask.getDescription());
        oldTask.setPriority(newTask.getPriority());
        oldTask.setStatus(newTask.getStatus());

        return taskRepo.save(oldTask);
    }

    @Transactional
    @Override
    public void deleteTask(ObjectId taskListId, ObjectId taskId) {
        TaskList taskList = taskListService.getTaskList(taskListId);
        List<Task> collected = taskList.getTasks().stream().filter(task->task.getId().equals(taskId)).toList();
        if(collected.isEmpty()) throw new IllegalArgumentException("Invalid Task Id!");
        Task task = taskRepo.findById(taskId).orElse(null);


        taskRepo.deleteById(taskId); /// 1

        taskList.getTasks().remove(task);
        taskListService.saveTaskList(taskList); /// 2
    }

    @Override
    public Task updateTagsOfTask(ObjectId taskListId, ObjectId taskId, String category) {
        TaskList taskList = taskListService.getTaskList(taskListId);
        List<Task> collected = taskList.getTasks().stream().filter(task->task.getId().equals(taskId)).toList();
        if(collected.isEmpty()) throw new IllegalArgumentException("Invalid Task Id!");
        Task task = collected.get(0);

        task.setCategory(category);
        return taskRepo.save(task); /// 1
    }
}
