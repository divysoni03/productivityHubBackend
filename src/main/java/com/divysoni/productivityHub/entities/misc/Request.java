package com.divysoni.productivityHub.entities.misc;

import com.divysoni.productivityHub.entities.taskManager.TaskList;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    private ObjectId id;

    @DBRef
    private TaskList taskList;

    private ObjectId senderId;

    private LocalDateTime created;
    private LocalDateTime expiration;

    public Request(TaskList taskList, ObjectId senderId, LocalDateTime created, LocalDateTime expiration) {
        this.taskList = taskList;
        this.senderId = senderId;
        this.created = created;
        this.expiration = expiration;
    }
}
