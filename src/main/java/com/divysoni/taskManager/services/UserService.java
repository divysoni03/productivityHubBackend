package com.divysoni.taskManager.services;

import com.divysoni.taskManager.entities.users.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveNewUser(User user);
    void saveUser(User user);
    User saveAdminUser(User user);

    List<User> getUsers();
    Optional<User> findById(ObjectId id);
    void deleteById(ObjectId id);

    User findByUserName(String userName);
}
