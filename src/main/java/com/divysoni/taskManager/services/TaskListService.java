package com.divysoni.taskManager.services;

import com.divysoni.taskManager.entities.taskManager.TaskList;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

public interface TaskListService {
    List<TaskList> getAllTaskList();
    TaskList saveNewTaskList(TaskList taskList);
    void saveTaskList(TaskList taskList);
    TaskList getTaskList(ObjectId id);
    TaskList updateTaskList(ObjectId taskListId, TaskList newTaskList);
    void deleteTaskList(ObjectId taskListsId);
}
