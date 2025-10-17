package com.divysoni.productivityHub.mappers.impl;

import com.divysoni.productivityHub.dto.TaskDto;
import com.divysoni.productivityHub.entities.taskManager.Task;
import com.divysoni.productivityHub.mappers.TaskMapper;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id()!=null?new ObjectId(taskDto.id()):null,
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}
