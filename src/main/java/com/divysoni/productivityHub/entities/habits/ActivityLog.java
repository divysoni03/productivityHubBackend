package com.divysoni.productivityHub.entities.habits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLog {
    private LocalDate date;
    private Map<ObjectId, Boolean> habitStatuses;

    public boolean isCompleted() {
        if (habitStatuses == null || habitStatuses.isEmpty()) return false;
        return habitStatuses.values().stream().allMatch(Boolean::booleanValue);
    }

    public double getCompletionPercentage() {
        if (habitStatuses == null || habitStatuses.isEmpty()) return 0.0;
        long completed = habitStatuses.values().stream().filter(v -> v).count();
        return (completed * 100.0) / habitStatuses.size();
    }
}
