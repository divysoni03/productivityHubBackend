package com.divysoni.taskManager.controller;

import com.divysoni.taskManager.dto.TaskListDto;
import com.divysoni.taskManager.entities.TaskList;
import com.divysoni.taskManager.mappers.impl.TaskListMapperImpl;
import com.divysoni.taskManager.services.impl.TaskListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListsController {

    @Autowired
    private TaskListMapperImpl taskListMapper;

    @Autowired
    private TaskListServiceImpl taskListService;

    @GetMapping
    public List<TaskListDto> listTaskList() {
        return taskListService.listTaskList()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){

        // first create taskList from received DTO and then save that entity in db
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );

        // then again convert the TaskList to TaskListDto and send back
        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListId){
        /*  getting the optional of taskList and then mapping it will function (taskListMapper::toDto)
                                                                                    ^              ^
                                                                                    |              |
        *                                                                      from which class   func
        * */
        return taskListService.getTaskList(taskListId)
                .map(taskListMapper::toDto);
    }

    @PutMapping("/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskListDto taskListDto) {
        TaskList updatedList = taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto));

        return taskListMapper.toDto(updatedList);
    }

    @DeleteMapping("/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {
        taskListService.deleteTaskList(taskListId);
    }
}
