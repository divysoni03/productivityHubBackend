package com.divysoni.taskManager.services.impl;

import com.divysoni.taskManager.entities.taskManager.TaskList;
import com.divysoni.taskManager.entities.users.User;
import com.divysoni.taskManager.repo.taskManager.TaskListRepo;
import com.divysoni.taskManager.services.TaskListService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListRepo taskListRepo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserAuthService userAuthService;

    @Override
    public List<TaskList> getAllTaskList() {
        User user = userAuthService.getAuthUser();
        List<TaskList> lists = user.getTaskLists();
        if(lists.isEmpty()) {
            return Collections.emptyList();
        }
        return lists;
    }

    @Transactional /// for adding new taskList
    @Override
    public TaskList saveNewTaskList(TaskList taskList) {
        if(null != taskList.getId()) {
            throw new IllegalArgumentException("TaskList already has an Id!");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("TaskList has invalid Title!");
        }

        LocalDateTime nowTime = LocalDateTime.now();
        taskList.setId(null);
        taskList.setCreated(nowTime);
        taskList.setUpdated(nowTime);

        User user = userAuthService.getAuthUser();
        user.getTaskLists().add(taskList);
        userService.saveUser(user); /// saving changes in the user as well

        return taskListRepo.save(taskList);
    }

    @Override /// only for editing the taskList
    public void saveTaskList(TaskList taskList) { taskListRepo.save(taskList); }

    @Override
    public TaskList getTaskList(ObjectId id) {
        User user = userAuthService.getAuthUser();
        List<TaskList> collected = user.getTaskLists().stream().filter(taskList -> taskList.getId().equals(id)).toList();
        if(!collected.isEmpty()) { /// checking if the taskList is exits in the user
            Optional<TaskList> taskLists = taskListRepo.findById(id);
            if(taskLists.isPresent()) {
                return taskLists.get();
            }
        }
        return new TaskList(); /// unless return empty taskList
    }

    @Override
    public TaskList updateTaskList(ObjectId taskListId, TaskList newTaskList) {
        User user = userAuthService.getAuthUser();
        List<TaskList> collected = user.getTaskLists().stream().filter(taskList -> taskList.getId().equals(taskListId)).toList();
        if(collected.isEmpty()){
            throw new IllegalArgumentException("TaskList doesn't exists, try with valid Id!");
        }

        TaskList oldTaskList = taskListRepo.findById(taskListId).orElseThrow(
                () -> new IllegalArgumentException("TaskList Not found")
        );

        LocalDateTime nowTime = LocalDateTime.now();
        oldTaskList.setTitle(!newTaskList.getTitle().isBlank() ? newTaskList.getTitle() : oldTaskList.getTitle());
        oldTaskList.setDescription(newTaskList.getDescription().isBlank() ? newTaskList.getDescription() : oldTaskList.getDescription());
        oldTaskList.setUpdated(nowTime);

        return taskListRepo.save(oldTaskList);
    }

    @Override
    public void deleteTaskList(ObjectId taskListId) {
        User user = userAuthService.getAuthUser();
        List<TaskList> collected = user.getTaskLists().stream().filter(taskList -> taskList.getId().equals(taskListId)).toList();
        if(collected.isEmpty()) {
            throw new IllegalArgumentException("TaskList Id doesn't belong to the user, please try again with valid Id!");
        }
        taskListRepo.deleteById(taskListId);
    }
}
