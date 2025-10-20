package com.divysoni.productivityHub.services.impl;

import com.divysoni.productivityHub.entities.habits.ActivityLog;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.entities.habits.HabitStorage;
import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.repo.habits.HabitStorageRepo;
import com.divysoni.productivityHub.services.HabitStorageService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class HabitStorageServiceImpl implements HabitStorageService {

    private final HabitStorageRepo habitStorageRepo;
    private final UserServiceImpl userService;

    public HabitStorageServiceImpl(HabitStorageRepo habitStorageRepo, @Lazy UserServiceImpl userService) {
        this.habitStorageRepo = habitStorageRepo;
        this.userService = userService;
    }

    @Override
    public HabitStorage saveHabitStorage(HabitStorage habitStorage) {
        return habitStorageRepo.save(habitStorage);
    }

    @Override
    public HabitStorage getHabitStorageForUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByUserName(name);

        return user.getHabitStorage();
    }

    @Override
    public Habit addHabit(String title) {
        HabitStorage storage = this.getHabitStorageForUser();
        storage.getHabits().add(new Habit(
                new ObjectId(),
                title,
                false
        ));
        int size = storage.getHabits().size();
        return habitStorageRepo.save(storage).getHabits().get(size-1);
    }

    @Override
    public List<Habit> getAllHabits() {
        HabitStorage storage = this.getHabitStorageForUser();
        return storage.getHabits();
    }

    @Override
    public void deleteHabitById(ObjectId id) {
        HabitStorage storage = this.getHabitStorageForUser();
        List<Habit> habits = storage.getHabits();
        habits.remove(habits.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .get()
        );
    }

    @Override
    public Habit updateHabitStatus(ObjectId id) {
        HabitStorage storage = this.getHabitStorageForUser();

        Habit habit = storage.getHabits().stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .get();
        habit.setStatus(true);

        habitStorageRepo.save(storage);
        return habit;
    }

    @Override
    public List<HabitStorage> getAllHabitStorage() {
        return habitStorageRepo.findAll();
    }

    ///  now the analysis
    @Override
    public Map<String, Object> getLastDaysData(int days) {
        HabitStorage storage = this.getHabitStorageForUser();
        List<ActivityLog> activities = storage.getActivity();

        int totalLogs = activities.size();
        if (totalLogs == 0) {
            return Map.of("message", "No activity data found");
        }

        ///  if fewer logs than requested
        int fromIndex = Math.max(0, totalLogs - days);
        List<ActivityLog> recentLogs = activities.subList(fromIndex, totalLogs);

        /// Analytics section
        long totalDays = recentLogs.size();
        long completedDays = recentLogs.stream()
                .filter(ActivityLog::isCompleted) // assuming your ActivityLog has a boolean isCompleted()
                .count();

        double completionRate = (completedDays * 100.0) / totalDays;

        // Calculate current streak (continuous completed days from the end)
        int currentStreak = 0;
        for (int i = recentLogs.size() - 1; i >= 0; i--) {
            if (recentLogs.get(i).isCompleted()) currentStreak++;
            else break;
        }

        // Reverse so data goes from oldest → newest
        List<ActivityLog> orderedLogs = new ArrayList<>(recentLogs);
        Collections.reverse(orderedLogs);

        // Return full analysis
        return Map.of(
                "totalDays", totalDays,
                "completedDays", completedDays,
                "completionRate", completionRate,
                "currentStreak", currentStreak,
                "activityData", orderedLogs
        );
    }

}
