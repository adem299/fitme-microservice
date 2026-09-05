package com.fitme.aiservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ActivityMessage (
    String id,
    String userid,
    Integer duration,
    String activityType,
    Integer caloriesBurned,
    LocalDateTime startTime,
    Map<String, Object> additionalMetrics,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){
}
