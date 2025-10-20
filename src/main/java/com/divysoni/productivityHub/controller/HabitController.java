package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.entities.habits.ActivityLog;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.services.impl.HabitStorageServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habit")
public class HabitController {
    public final HabitStorageServiceImpl habitStorageService;

    public HabitController(HabitStorageServiceImpl habitStorageService) {
        this.habitStorageService = habitStorageService;
    }

    @PostMapping
    public Habit saveNewHabit(@RequestParam String title) {
        return habitStorageService.addHabit(title);
    }

    @GetMapping
    public List<Habit> getAllHabits(){
        return habitStorageService.getAllHabits();
    }

    @DeleteMapping("/{habit_id}")
    public void deleteHabitById(@PathVariable("habit_id") ObjectId id) {
        habitStorageService.deleteHabitById(id);
    }

    @PatchMapping("/{habit_id}")
    public Habit updateHabitById(@PathVariable("habit_id") ObjectId id){
        return habitStorageService.updateHabitStatus(id);
    }

    @GetMapping("/getAnalysis")
    public Map<String, Object> getAnalysis(@RequestParam int days) {
        return habitStorageService.getLastDaysData(days);
    }

}
