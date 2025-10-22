package com.divysoni.productivityHub.dto.habit;

import com.divysoni.productivityHub.entities.habits.Habit;
import java.util.List;

public record HabitStorageDto(
        String id,
        int no_of_habits,
        double progress,
        List<Habit> habits
) {
}
