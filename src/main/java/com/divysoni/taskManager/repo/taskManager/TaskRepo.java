package com.divysoni.taskManager.repo.taskManager;

import com.divysoni.taskManager.entities.taskManager.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends MongoRepository<Task, ObjectId> {}