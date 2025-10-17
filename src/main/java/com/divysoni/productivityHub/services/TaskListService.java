package com.divysoni.productivityHub.services;

import com.divysoni.productivityHub.entities.taskManager.TaskList;
import org.bson.types.ObjectId;
import java.util.List;

public interface TaskListService {
    List<TaskList> getAllTaskList();
    TaskList saveNewTaskList(TaskList taskList);
    void saveTaskList(TaskList taskList);
    TaskList getTaskList(ObjectId id);
    TaskList updateTaskList(ObjectId taskListId, TaskList newTaskList);
    void deleteTaskList(ObjectId taskListsId);
}
