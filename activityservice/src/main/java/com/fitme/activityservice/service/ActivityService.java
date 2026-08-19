package com.fitme.activityservice.service;

import org.springframework.stereotype.Service;

import com.fitme.activityservice.dto.ActivityRequest;
import com.fitme.activityservice.dto.ActivityResponse;
import com.fitme.activityservice.mapper.ActivityMapper;
import com.fitme.activityservice.model.Activity;
import com.fitme.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityResponse trackActivity(ActivityRequest request) {
        return activityMapper.toActivityResponse(
            activityRepository.save(activityMapper.toActivity(request))
        );
    }
}
