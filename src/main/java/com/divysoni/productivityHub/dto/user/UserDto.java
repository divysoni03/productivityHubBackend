package com.divysoni.productivityHub.dto.user;

import com.divysoni.productivityHub.dto.habit.HabitStorageDto;
import com.divysoni.productivityHub.entities.taskManager.TaskList;

import java.util.List;

public record UserDto(
        String id,
        String userName,
        String password,
        String email,
        List<TaskList> taskLists,
        HabitStorageDto habitStorageDto
) {}
