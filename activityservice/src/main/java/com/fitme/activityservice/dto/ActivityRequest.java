package com.fitme.activityservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fitme.activityservice.model.ActivityType;

public record ActivityRequest(
    String userId,
    ActivityType type,
    Integer duration,
    Integer caloriesBurned,
    LocalDateTime startTime,
    Map<String, Object> additionalMetrics
) {
    public ActivityRequest(String userId, ActivityType type, Integer duration, Integer caloriesBurned, LocalDateTime startTime) {
        this(userId, type, duration, caloriesBurned, startTime, null);
    }
}
