package com.divysoni.productivityHub.entities.habits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "habit_storage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitStorage {
    @Id
    private ObjectId id;

    private List<Habit> habits = new ArrayList<>();
    private List<ActivityLog> activity = new ArrayList<>();
}

