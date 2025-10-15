package com.divysoni.taskManager.services;

import com.divysoni.taskManager.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskList();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList(UUID taskListId, TaskList newTaskList);
    void deleteTaskList(UUID taskListsId);
}
