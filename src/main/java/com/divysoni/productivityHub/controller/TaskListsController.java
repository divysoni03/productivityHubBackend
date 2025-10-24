package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.dto.misc.CustomResponse;
import com.divysoni.productivityHub.dto.misc.ResponseBuilder;
import com.divysoni.productivityHub.dto.taskManager.TaskListDto;
import com.divysoni.productivityHub.mappers.impl.TaskListMapperImpl;
import com.divysoni.productivityHub.services.impl.TaskListServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListsController {

    private final TaskListMapperImpl taskListMapper;
    private final TaskListServiceImpl taskListService;

    public TaskListsController(TaskListMapperImpl taskListMapper, TaskListServiceImpl taskListService) {
        this.taskListMapper = taskListMapper;
        this.taskListService = taskListService;
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<TaskListDto>>> getAllTaskLists() {
        List<TaskListDto> response = taskListService.getAllTaskList()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
        return ResponseBuilder.success("Fetched TaskLists Successfully!", response);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<TaskListDto>> createTaskList(@RequestBody TaskListDto taskListDto){
        TaskListDto response = taskListMapper.toDto(taskListService.saveNewTaskList(
                taskListMapper.fromDto(taskListDto)
        ));
        return ResponseBuilder.created("TaskList Created Successfully!", response);
    }

    @GetMapping("/{task_list_id}")
    public ResponseEntity<CustomResponse<TaskListDto>> getTaskList(@PathVariable("task_list_id") ObjectId taskListId){
        TaskListDto response = taskListMapper.toDto(taskListService.getTaskList(taskListId));
        return ResponseBuilder.success("Fetched TaskList Successfully!", response);
    }

    @PutMapping("/{task_list_id}")
    public ResponseEntity<CustomResponse<TaskListDto>> updateTaskList(@PathVariable("task_list_id") ObjectId taskListId, @RequestBody TaskListDto taskListDto) {
        TaskListDto response = taskListMapper.toDto(taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto)));
        return ResponseBuilder.success("TaskList Updated Successfully!", response);
    }

    @DeleteMapping("/{task_list_id}")
    public ResponseEntity<CustomResponse<Object>> deleteTaskList(@PathVariable("task_list_id") ObjectId taskListId) {
        taskListService.deleteTaskList(taskListId);
        return ResponseBuilder.success("TaskList Deleted Successfully!", null);
    }
}
