package com.divysoni.productivityHub.repo.taskManager;

import com.divysoni.productivityHub.entities.taskManager.TaskList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepo extends MongoRepository<TaskList, ObjectId> {}
