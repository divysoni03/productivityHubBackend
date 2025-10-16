package com.divysoni.taskManager.mappers;

import com.divysoni.taskManager.dto.TaskDto;
import com.divysoni.taskManager.entities.taskManager.Task;

public interface TaskMapper {

    // custom mapper which does conversion between dto and object/entity
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
