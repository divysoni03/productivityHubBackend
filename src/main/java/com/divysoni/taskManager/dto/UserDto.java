package com.divysoni.taskManager.dto;

import com.divysoni.taskManager.entities.taskManager.TaskList;
import org.bson.types.ObjectId;

import java.util.List;

public record UserDto(
        ObjectId id,
        String userName,
        String password,
        String email,
        List<TaskList> taskLists
) {}
