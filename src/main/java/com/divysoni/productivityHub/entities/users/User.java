package com.divysoni.productivityHub.entities.users;

import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.entities.habits.HabitStorage;
import com.divysoni.productivityHub.entities.misc.Request;
import com.divysoni.productivityHub.entities.taskManager.TaskList;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
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

    @DBRef
    private HabitStorage habitStorage;

    private List<String> roles;

    @DBRef
    private List<Request> requests = new ArrayList<>();
}
