package com.divysoni.productivityHub.mappers.impl;

import com.divysoni.productivityHub.dto.habit.HabitDto;
import com.divysoni.productivityHub.dto.habit.HabitStorageDto;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.entities.habits.HabitStorage;
import com.divysoni.productivityHub.mappers.HabitStorageMapper;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;


@Component
public class HabitStorageMapperImpl implements HabitStorageMapper {
    @Override
    public HabitStorage fromDto(HabitStorageDto habitStorageDto) {
        return new HabitStorage(
                habitStorageDto.id()!=null?new ObjectId(habitStorageDto.id()):null,
                null,
                null
        );
    }

    @Override
    public HabitStorageDto toDto(HabitStorage habitStorage) {
        return new HabitStorageDto(
                habitStorage.getId().toString(),
                habitStorage.getHabits().size(),
                (double) habitStorage.getHabits().stream().filter(habit -> habit.getStatus().equals(Boolean.TRUE)).toList().size() / habitStorage.getHabits().size(),
                habitStorage.getHabits()
        );
    }

    @Override
    public HabitDto toDto(Habit habit) {
        return new HabitDto(
                habit.getId().toString(),
                habit.getTitle(),
                habit.getStatus()
        );
    }
}
