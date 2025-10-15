package com.divysoni.taskManager.controller;

import com.divysoni.taskManager.dto.TaskDto;
import com.divysoni.taskManager.entities.Task;
import com.divysoni.taskManager.mappers.TaskMapper;
import com.divysoni.taskManager.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists/{task_list_id}/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.getTasks(taskListId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task task = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(task);

    }

    @GetMapping("/{task_id}")
    public Optional<TaskDto> getTaskById(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId){
        return taskService.getTaskById(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDto updateTaskById(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId, @RequestBody TaskDto newTask) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(newTask));

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{task_id}")
    public void deleteTaskById(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
