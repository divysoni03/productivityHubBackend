package com.divysoni.productivityHub.dto.misc;

import com.divysoni.productivityHub.entities.taskManager.TaskList;

import java.time.LocalDateTime;

public record RequestDto(
        String id,
        String taskListId,
        String senderId,
        LocalDateTime created,
        LocalDateTime expiration
) {
}
