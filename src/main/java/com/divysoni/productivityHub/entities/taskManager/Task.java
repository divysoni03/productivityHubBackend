package com.divysoni.productivityHub.entities.taskManager;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String description;

    @NonNull
    private LocalDateTime dueDate;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String tags;
}
