package com.divysoni.productivityHub.services.impl;

import com.divysoni.productivityHub.entities.misc.Request;
import com.divysoni.productivityHub.entities.taskManager.TaskList;
import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.repo.misc.RequestRepo;
import com.divysoni.productivityHub.services.RequestService;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepo requestRepo;
    private final UserServiceImpl userService;
    private final TaskListServiceImpl taskListService;

    public RequestServiceImpl(RequestRepo requestRepo, UserServiceImpl userService, TaskListServiceImpl taskListService) {
        this.requestRepo = requestRepo;
        this.userService = userService;
        this.taskListService = taskListService;
    }

    @Override
    public List<Request> getAllRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findByUserName(userName);
        if(user.getRequests().isEmpty()) {
            return Collections.emptyList();
        }
        return user.getRequests();
    }

    @Transactional
    @Override
    public Request makeNewRequest(ObjectId taskListId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);

        TaskList taskList = taskListService.getTaskList(taskListId);

        LocalDateTime creationTime = LocalDateTime.now();
        LocalDateTime expirationTime = creationTime.plusDays(2);
        Request newRequest = new Request(
                null,
                taskList,
                user.getId(),
                creationTime,
                expirationTime
        );

        Request savedRequest = requestRepo.save(newRequest);
        user.getRequests().add(savedRequest);

        return savedRequest;
    }

    @Override
    public void deleteById(ObjectId requestId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findByUserName(userName);
        List<Request> collected = user.getRequests().stream().filter(req -> req.getId().equals(requestId)).toList();

        if(collected.isEmpty()) {
            throw new IllegalArgumentException("Invalid Id, enter valid Id");
        }
        requestRepo.deleteById(requestId);
    }

    @Transactional
    @Override
    public void acceptRequest(ObjectId requestId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findByUserName(userName);

        List<Request> collected = user.getRequests().stream().filter(req -> req.getId().equals(requestId)).toList();
        if(collected.isEmpty()) {
            throw new IllegalArgumentException("Invalid Id, enter valid Id");
        }
        Optional<Request> requests = requestRepo.findById(requestId);
        if(requests.isEmpty()){
            throw new IllegalArgumentException("Invalid Id, enter again!");
        }
        Request request = requests.get();
        user.getTaskLists().add(request.getTaskList());

        user.getRequests().remove(request);
        requestRepo.deleteById(requestId);

        userService.saveUser(user);
    }
}
