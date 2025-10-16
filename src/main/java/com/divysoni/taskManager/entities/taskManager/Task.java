package com.divysoni.taskManager.entities.taskManager;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    private LocalDateTime dueDate;

    @NonNull
    private TaskStatus status;

    @NonNull
    private TaskPriority priority;

    @NonNull
    private LocalDateTime created;

    @NonNull
    private LocalDateTime updated;
}
