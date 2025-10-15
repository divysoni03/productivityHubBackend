package com.divysoni.taskManager.mappers;

import com.divysoni.taskManager.dto.TaskListDto;
import com.divysoni.taskManager.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
