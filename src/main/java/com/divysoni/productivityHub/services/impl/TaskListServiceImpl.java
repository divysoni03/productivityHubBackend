package com.divysoni.productivityHub.services.impl;

import com.divysoni.productivityHub.entities.taskManager.TaskList;
import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.repo.taskManager.TaskListRepo;
import com.divysoni.productivityHub.services.TaskListService;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepo taskListRepo;
    private final UserServiceImpl userService;

    public TaskListServiceImpl(TaskListRepo taskListRepo, UserServiceImpl userService) {
        this.taskListRepo=taskListRepo;
        this.userService=userService;
    }

    @Override
    public List<TaskList> getAllTaskList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);

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
        if(taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("TaskList has invalid Title!");
        }

        LocalDateTime nowTime = LocalDateTime.now();
        taskList.setCreated(nowTime);
        taskList.setUpdated(nowTime);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);

        TaskList savedTaskList = taskListRepo.save(taskList);

        user.getTaskLists().add(savedTaskList);
        userService.saveUser(user); /// saving changes in the user as well

        return savedTaskList;
    }

    @Override /// only for editing the taskList
    public void saveTaskList(TaskList taskList) { taskListRepo.save(taskList); }

    @Override
    public TaskList getTaskList(ObjectId id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);

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

    @Transactional
    @Override
    public void deleteTaskList(ObjectId taskListId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);

        List<TaskList> collected = user.getTaskLists().stream().filter(taskList -> taskList.getId().equals(taskListId)).toList();
        if(collected.isEmpty()) {
            throw new IllegalArgumentException("TaskList Id doesn't belong to the user, please try again with valid Id!");
        }
        user.getTaskLists().removeIf(x -> x.getId().equals(taskListId));
        taskListRepo.deleteById(taskListId);
        userService.saveUser(user);
    }
}
