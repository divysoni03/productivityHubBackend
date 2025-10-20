package com.divysoni.productivityHub.services.schedulars;

import com.divysoni.productivityHub.entities.habits.ActivityLog;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.entities.habits.HabitStorage;
import com.divysoni.productivityHub.services.impl.HabitStorageServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HabitScheduler {

    private final HabitStorageServiceImpl habitStorageService;

    public HabitScheduler(HabitStorageServiceImpl habitStorageService) {
        this.habitStorageService = habitStorageService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void logAndResetHabit() {
        List<HabitStorage> allStorages = habitStorageService.getAllHabitStorage();

        for (HabitStorage storage : allStorages) {
            Map<ObjectId, Boolean> habitStatuses = new HashMap<>();
            for (Habit habit : storage.getHabits()) {
                habitStatuses.put(habit.getId(), habit.getStatus());
                habit.setStatus(false); // reset for new day
            }

            ActivityLog log = new ActivityLog(LocalDate.now(), habitStatuses);
            storage.getActivity().add(log);
            habitStorageService.saveHabitStorage(storage);
        }
    }

}