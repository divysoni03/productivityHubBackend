package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.dto.misc.CustomResponse;
import com.divysoni.productivityHub.dto.misc.ResponseBuilder;
import com.divysoni.productivityHub.dto.habit.HabitDto;
import com.divysoni.productivityHub.entities.habits.Habit;
import com.divysoni.productivityHub.mappers.impl.HabitStorageMapperImpl;
import com.divysoni.productivityHub.services.impl.HabitStorageServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habit")
public class HabitController {
    private final HabitStorageServiceImpl habitStorageService;
    private final HabitStorageMapperImpl habitMapper;

    public HabitController(HabitStorageServiceImpl habitStorageService, HabitStorageMapperImpl habitMapper) {
        this.habitStorageService = habitStorageService;
        this.habitMapper = habitMapper;
    }

    @PostMapping
    public ResponseEntity<CustomResponse<HabitDto>> saveNewHabit(@RequestParam String title) {
        return ResponseBuilder.success("Habit saved Successfully!", habitMapper.toDto(habitStorageService.addHabit(title)));
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<HabitDto>>> getAllHabits(){
        return ResponseBuilder.success("Habits Fetched Successfully!", habitStorageService.getAllHabits().stream().map(habitMapper::toDto).toList());
    }

    @DeleteMapping("/{habit_id}")
    public void deleteHabitById(@PathVariable("habit_id") ObjectId id) {
        habitStorageService.deleteHabitById(id);
    }

    @PatchMapping("/{habit_id}")
    public ResponseEntity<CustomResponse<Habit>> updateHabitStatus(@PathVariable("habit_id") ObjectId id){
        return ResponseBuilder.success("Habit Status updated!", habitStorageService.updateHabitStatus(id));
    }

    @GetMapping("/getAnalysis")
    public ResponseEntity<CustomResponse<Map<String, Object>>> getAnalysis(@RequestParam int days) {
        return ResponseBuilder.success("Activity for " + days + "fetched!", habitStorageService.getLastDaysData(days));
    }

}
