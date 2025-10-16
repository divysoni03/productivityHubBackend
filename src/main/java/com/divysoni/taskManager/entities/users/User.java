package com.divysoni.taskManager.entities.users;

import com.divysoni.taskManager.entities.taskManager.TaskList;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Generated
    private ObjectId id;

    @Indexed(unique = true) // making it unique
    @NonNull
    private String userName;
    @NonNull
    private String password;

    @NonNull
    private String email;
    private boolean weeklyAnalysis;

    @DBRef
    private List<TaskList> taskLists =  new ArrayList<>();
    private List<String> roles;
}
