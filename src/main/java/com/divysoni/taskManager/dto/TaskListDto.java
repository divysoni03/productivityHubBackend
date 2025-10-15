package com.divysoni.taskManager.dto;

import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count, // count of the task we have in the list
        Double progress, // no.of tasks completed in the list
        List<TaskDto> tasks
) {
}
