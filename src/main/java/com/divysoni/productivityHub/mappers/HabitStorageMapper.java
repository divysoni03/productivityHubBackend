package com.divysoni.productivityHub.mappers;

import com.divysoni.productivityHub.dto.habit.HabitDto;
import com.divysoni.productivityHub.dto.habit.HabitStorageDto;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.entities.habits.HabitStorage;

public interface HabitStorageMapper {
    HabitStorage fromDto(HabitStorageDto habitStorageDto);
    HabitStorageDto toDto(HabitStorage habitStorage);

    HabitDto toDto(Habit habit);
}
