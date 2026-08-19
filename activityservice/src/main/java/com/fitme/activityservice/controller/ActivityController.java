package com.fitme.activityservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitme.activityservice.dto.ActivityRequest;
import com.fitme.activityservice.dto.ActivityResponse;
import com.fitme.activityservice.dto.ApiResponse;
import com.fitme.activityservice.service.ActivityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ActivityResponse>> trackActivity(@RequestBody ActivityRequest request) {
        ActivityResponse response = activityService.trackActivity(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Activity tracked successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityResponse>>> getUserActivities(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success("User activities retrieved successfully", activityService.getUserActivities(userId)));
    } 
}
