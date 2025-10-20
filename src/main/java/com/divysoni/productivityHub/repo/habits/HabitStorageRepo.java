package com.divysoni.productivityHub.repo.habits;

import com.divysoni.productivityHub.entities.habits.HabitStorage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitStorageRepo extends MongoRepository<HabitStorage, ObjectId> {}
