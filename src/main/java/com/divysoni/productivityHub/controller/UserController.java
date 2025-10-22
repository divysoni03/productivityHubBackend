package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.dto.CustomResponse;
import com.divysoni.productivityHub.dto.ResponseBuilder;
import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PutMapping("/enable-weekly-analysis")
    public ResponseEntity<CustomResponse<Object>> enableWeeklyAnalysis() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        user.setWeeklyAnalysis(true);
        userService.saveUser(user);
        return ResponseBuilder.success("Weekly Analysis Enabled Successfully!", null);
    }

    @PutMapping
    public ResponseEntity<CustomResponse<User>> editUser(@RequestBody User newUser) {
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
                return ResponseBuilder.success("User Details updated Successfully!", null);
            }
            return ResponseBuilder.failure(HttpStatus.NOT_FOUND, "Unable to find the User.");
        } catch (Exception e) {
            return ResponseBuilder.failure(HttpStatus.NOT_FOUND, "Unable to find the User.");
        }
    }

    @DeleteMapping
    public ResponseEntity<CustomResponse<Object>> deleteUser() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            User oldUser = userService.findByUserName(userName);
            if(oldUser != null) {
                userService.deleteById(oldUser.getId());
                return ResponseBuilder.success("Deleted User Successfully!", null);
            }
            return ResponseBuilder.failure(HttpStatus.NOT_FOUND, "Unable to find the User.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure(HttpStatus.NOT_FOUND, "UserName Not Found!");
        }
    }
}
