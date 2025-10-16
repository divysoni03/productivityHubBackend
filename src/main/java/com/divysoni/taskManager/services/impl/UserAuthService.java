package com.divysoni.taskManager.services.impl;

import com.divysoni.taskManager.entities.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthService {

    @Autowired
    private UserServiceImpl userService;

    @Bean
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.findByUserName(userName);
    }
}
