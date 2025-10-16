package com.divysoni.taskManager.controller;

import com.divysoni.taskManager.entities.users.User;
import com.divysoni.taskManager.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User newUser) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            User oldUser = userService.findByUserName(userName);
            if(oldUser != null) {
                // Update username if provided
                if(newUser.getUserName() != null && !newUser.getUserName().isEmpty()) {
                    oldUser.setUserName(newUser.getUserName());
                }

                // Update password if provided
                if(newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                    oldUser.setPassword(newUser.getPassword()); // Password will be encrypted in UserService.saveUser()
                }

                userService.saveNewUser(oldUser); // This will encrypt the password because the new user's pass is not encrypted yet
                return new ResponseEntity<>(oldUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User oldUser = userService.findByUserName(userName);
        if(oldUser != null) {
            userService.deleteById(oldUser.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
