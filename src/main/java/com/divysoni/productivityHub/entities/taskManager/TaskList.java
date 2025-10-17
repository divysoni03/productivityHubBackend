package com.divysoni.productivityHub.entities.taskManager;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "task_lists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskList {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String description;

    @DBRef
    private List<Task> tasks = new ArrayList<>();

    private LocalDateTime created;

    private LocalDateTime updated;
}
