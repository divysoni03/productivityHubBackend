package com.divysoni.productivityHub.dto;

import java.util.List;

public record TaskListDto(
        String id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks
) {}
