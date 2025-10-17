package com.divysoni.productivityHub.dto;

import com.divysoni.productivityHub.entities.taskManager.TaskPriority;
import com.divysoni.productivityHub.entities.taskManager.TaskStatus;
import java.time.LocalDateTime;

/* DTO(data transfer object): which is just used to transfer object/ presentation object
* ex., from frontend to server etc...
* DTO has nothing to do with entity */

/*Java records are a special type of class introduced in Java 14 as a preview feature and standardized in Java 16, designed to serve as transparent, immutable data carriers with minimal boilerplate code.
 They automatically generate a canonical constructor, accessor methods for each component, and implementations for equals(), hashCode(), and toString() based on the declared fields, which are implicitly final.
 This makes records ideal for creating Plain Old Java Objects (POJOs) and Data Transfer Objects (DTOs) without the need to manually write repetitive code.*/
public record TaskDto(
        // each of these variables are immutable
        String id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
){}
