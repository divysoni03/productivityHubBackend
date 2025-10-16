package com.divysoni.taskManager.dto;

import org.bson.types.ObjectId;
import java.util.List;

public record TaskListDto(
        ObjectId id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks
) {}
