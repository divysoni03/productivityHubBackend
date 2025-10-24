package com.divysoni.productivityHub.repo.misc;

import com.divysoni.productivityHub.entities.misc.Request;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends MongoRepository<Request, ObjectId> {}
