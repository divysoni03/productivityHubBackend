package com.divysoni.taskManager.mappers.impl;

import com.divysoni.taskManager.dto.TaskListDto;
import com.divysoni.taskManager.entities.taskManager.Task;
import com.divysoni.taskManager.entities.taskManager.TaskList;
import com.divysoni.taskManager.entities.taskManager.TaskStatus;
import com.divysoni.taskManager.mappers.TaskListMapper;
import com.divysoni.taskManager.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream().map(taskMapper::toDto).toList())
                        .orElse(null)
        );
    }


    private Double calculateTaskListProgress(List<Task> tasks) {
        if(tasks == null ) return null;

        long closedTaskCount = tasks.stream().filter(task ->
                TaskStatus.CLOSED == task.getStatus()
        ).count();

        return (double) (closedTaskCount / (tasks.isEmpty()?1:tasks.size()) );
    }
}
