package com.fitme.aiservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitme.aiservice.dto.ApiResponse;
import com.fitme.aiservice.model.Recommendation;
import com.fitme.aiservice.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Recommendation>>> getUserRecommendation(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success("User recommendations retrieved successfully", recommendationService.getUserRecommendation(userId)));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ApiResponse<Recommendation>> getActivityRecommendation(@PathVariable String activityId) {
        return ResponseEntity.ok(ApiResponse.success("Activity recommendations retrieved successfully", recommendationService.getActivityRecommendation(activityId)));
    }
}
