package com.divysoni.productivityHub.services;

import com.divysoni.productivityHub.entities.habits.ActivityLog;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.entities.habits.HabitStorage;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface HabitStorageService {
    HabitStorage saveHabitStorage(HabitStorage habitStorage);
    HabitStorage getHabitStorageForUser();
    Habit addHabit(String title);
    List<Habit> getAllHabits();
    void deleteHabitById(ObjectId id);
    Habit updateHabitStatus(ObjectId id);
    List<HabitStorage> getAllHabitStorage();
    Map<String, Object> getLastDaysData(int days);
}
