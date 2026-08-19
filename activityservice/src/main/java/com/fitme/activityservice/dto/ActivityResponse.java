package com.fitme.activityservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fitme.activityservice.model.ActivityType;

public record ActivityResponse (
    String id,
    String userId,
    ActivityType type,
    Integer duration,
    Integer caloriesBurned,
    LocalDateTime startTime,
    Map<String, Object> additionalMetrics,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
