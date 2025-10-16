package com.divysoni.taskManager.repo.taskManager;

import com.divysoni.taskManager.entities.taskManager.TaskList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TaskListRepo extends MongoRepository<TaskList, ObjectId> {}
