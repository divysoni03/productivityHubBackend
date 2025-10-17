package com.divysoni.productivityHub.repo.user;

import com.divysoni.productivityHub.entities.users.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
}
