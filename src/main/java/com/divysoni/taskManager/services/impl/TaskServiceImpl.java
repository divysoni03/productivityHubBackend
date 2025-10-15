package com.divysoni.taskManager.services.impl;

import com.divysoni.taskManager.entities.Task;
import com.divysoni.taskManager.entities.TaskList;
import com.divysoni.taskManager.entities.TaskPriority;
import com.divysoni.taskManager.entities.TaskStatus;
import com.divysoni.taskManager.repo.TaskListRepo;
import com.divysoni.taskManager.repo.TaskRepo;
import com.divysoni.taskManager.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskListRepo taskListRepo;

    @Override
    public List<Task> getTasks(UUID taskListId) {
        return taskRepo.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null != task.getId()) {
            throw new IllegalArgumentException("Task already has an Id!");
        }
        if(null == task.getTitle()) {
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepo.findById(taskListId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid TaskList Id provided!"));

        LocalDateTime nowTime = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList, // the taskList where we are adding the task
                nowTime, //  created
                nowTime // updated
        );

        return taskRepo.save(taskToSave);
    }

    @Override
    public Optional<Task> getTaskById(UUID taskListId, UUID taskId) {
        return taskRepo.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task newTask) {
        if(null == newTask.getId()) {
            throw new IllegalArgumentException("Task must have an Id!");
        }
        if(!Objects.equals(taskId, newTask.getId())) {
            throw new IllegalArgumentException("Task IDs do not match!");
        }
        if(null == newTask.getPriority()) {
            throw new IllegalArgumentException("Task must have a valid priority!");
        }
        if(null == newTask.getStatus()) {
            throw new IllegalArgumentException("Task must have a valid status!");
        }

        Task oldTask = taskRepo.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(()-> new IllegalArgumentException("Task Not found!"));

        oldTask.setTitle(Objects.equals(newTask.getTitle(), null)? oldTask.getTitle() : newTask.getTitle());
        oldTask.setDescription(Objects.equals(newTask.getDescription(), null)? oldTask.getDescription() : newTask.getDescription());
        oldTask.setPriority(Objects.equals(newTask.getPriority(), null)? oldTask.getPriority() : newTask.getPriority());
        oldTask.setStatus(Objects.equals(newTask.getStatus(), null)? oldTask.getStatus() : newTask.getStatus());

        return taskRepo.save(oldTask);
    }

    /* because we made our own delete method which delete from both taskList and task database
    * if one operation fails then database will be inconsistent
    * that's why we use transactional so if something goes wrong then it can roll back*/
    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepo.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
