package com.fitme.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitme.aiservice.exception.ActivityNotFoundException;
import com.fitme.aiservice.model.Recommendation;
import com.fitme.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: " + activityId));
    }    
}
