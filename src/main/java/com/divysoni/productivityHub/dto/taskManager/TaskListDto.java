package com.divysoni.productivityHub.dto.taskManager;

import com.divysoni.productivityHub.entities.taskManager.TaskListCategory;

import java.util.List;

public record TaskListDto(
        String id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks,
        TaskListCategory category
) {}
