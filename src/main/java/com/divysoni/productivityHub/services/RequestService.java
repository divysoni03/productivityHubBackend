package com.divysoni.productivityHub.services;

import com.divysoni.productivityHub.entities.misc.Request;
import org.bson.types.ObjectId;

import java.util.List;

public interface RequestService {
    List<Request> getAllRequest();
    Request makeNewRequest(ObjectId taskListId);
    void deleteById(ObjectId requestId);
    void acceptRequest(ObjectId requestId);
}
