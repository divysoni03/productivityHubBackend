package com.divysoni.productivityHub.mappers;

import com.divysoni.productivityHub.dto.taskManager.TaskListDto;
import com.divysoni.productivityHub.entities.taskManager.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
