package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.dto.misc.CustomResponse;
import com.divysoni.productivityHub.dto.misc.ResponseBuilder;
import com.divysoni.productivityHub.dto.taskManager.TaskDto;
import com.divysoni.productivityHub.mappers.impl.TaskMapperImpl;
import com.divysoni.productivityHub.services.impl.TaskServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final TaskMapperImpl taskMapper;

    public TaskController (TaskServiceImpl taskService, TaskMapperImpl taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<TaskDto>>> getTasks(@PathVariable("task_list_id") ObjectId taskListId) {
        List<TaskDto> response = taskService.getTasks(taskListId).stream()
                .map(taskMapper::toDto)
                .toList();
        return ResponseBuilder.success("Fetched tasks successfully!", response);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<TaskDto>> createTask(@PathVariable("task_list_id") ObjectId taskListId, @RequestBody TaskDto taskDto) {
        TaskDto response = taskMapper.toDto(taskService.createTask(taskListId, taskMapper.fromDto(taskDto)));
        return ResponseBuilder.created("Created task successfully!", response);
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<CustomResponse<TaskDto>> getTaskById(@PathVariable("task_list_id") ObjectId taskListId, @PathVariable("task_id") ObjectId taskId){
        TaskDto response = taskMapper.toDto(taskService.getTaskById(taskListId, taskId));
        return ResponseBuilder.success("Fetched task successfully!", response);
    }

    @PutMapping("/{task_id}")
    public ResponseEntity<CustomResponse<TaskDto>> updateTaskById(@PathVariable("task_list_id") ObjectId taskListId, @PathVariable("task_id") ObjectId taskId, @RequestBody TaskDto newTask) {
        TaskDto response = taskMapper.toDto(taskService.updateTask(taskListId, taskId, taskMapper.fromDto(newTask)));
        return ResponseBuilder.success("Task Updated Successfully!", response);
    }

    @PatchMapping("/{task_id}/update")
    public ResponseEntity<CustomResponse<TaskDto>> updateTagsOfTask(@PathVariable("task_list_id") ObjectId taskListId, @PathVariable("task_id") ObjectId taskId, @RequestParam String tags) {
        TaskDto response = taskMapper.toDto(taskService.updateTagsOfTask(taskListId, taskId, tags));
        return ResponseBuilder.success("Tag Added successfully!", response);
    }

    @DeleteMapping("/{task_id}")
    public ResponseEntity<CustomResponse<Object>> deleteTaskById(@PathVariable("task_list_id") ObjectId taskListId, @PathVariable("task_id") ObjectId taskId) {
        taskService.deleteTask(taskListId, taskId);
        return ResponseBuilder.success("Task deleted Successfully!", null);
    }
}
