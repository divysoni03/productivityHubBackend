package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.entities.habits.HabitStorage;
import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.services.impl.HabitStorageServiceImpl;
import com.divysoni.productivityHub.services.impl.UserDetailsServiceImpl;
import com.divysoni.productivityHub.services.impl.UserServiceImpl;
import com.divysoni.productivityHub.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtils;
    private final HabitStorageServiceImpl habitStorageService;

    public PublicController(UserServiceImpl userService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtils, HabitStorageServiceImpl habitStorageService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.habitStorageService = habitStorageService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        try {
            HabitStorage savedHabitStorage = habitStorageService.saveHabitStorage(new HabitStorage());
            user.setHabitStorage(savedHabitStorage);
            User savedUser = userService.saveNewUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try{
            /* step.1 this authentication manager internally uses userDetailsServiceImpl to verify the user */
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

            /* step.2 get user details */
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());

            /* step.3 make JWT token with help of this userDetails */
            String jwt = jwtUtils.generateToken(userDetails.getUsername());

            HttpHeaders headers = new HttpHeaders();
            headers.add("token", jwt);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);

        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Incorrect Username of Password", HttpStatus.BAD_REQUEST);
        }
    }
}
