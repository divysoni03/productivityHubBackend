package com.divysoni.productivityHub.mappers;

import com.divysoni.productivityHub.dto.TaskDto;
import com.divysoni.productivityHub.entities.taskManager.Task;

public interface TaskMapper {

    // custom mapper which does conversion between dto and object/entity
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
