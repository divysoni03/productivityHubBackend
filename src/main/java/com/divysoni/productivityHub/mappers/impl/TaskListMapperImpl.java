package com.divysoni.productivityHub.mappers.impl;

import com.divysoni.productivityHub.dto.taskManager.TaskListDto;
import com.divysoni.productivityHub.entities.taskManager.Task;
import com.divysoni.productivityHub.entities.taskManager.TaskList;
import com.divysoni.productivityHub.entities.taskManager.TaskStatus;
import com.divysoni.productivityHub.mappers.TaskListMapper;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapperImpl taskMapper;

    public TaskListMapperImpl(TaskMapperImpl taskMapper) {
        this.taskMapper= taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id()!=null?new ObjectId(taskListDto.id()):null,
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
                taskList.getId().toString(),
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
