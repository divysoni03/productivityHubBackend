package com.divysoni.productivityHub.services.impl;

import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.repo.user.UserRepo;
import com.divysoni.productivityHub.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepo.save(user);
    }
    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }
    @Override
    public User saveAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        return userRepo.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findById(ObjectId id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteById(ObjectId id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }
}
