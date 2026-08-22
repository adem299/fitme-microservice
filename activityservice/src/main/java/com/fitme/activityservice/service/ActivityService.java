package com.fitme.activityservice.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fitme.activityservice.dto.ActivityRequest;
import com.fitme.activityservice.dto.ActivityResponse;
import com.fitme.activityservice.exception.UserNotFoundException;
import com.fitme.activityservice.mapper.ActivityMapper;
import com.fitme.activityservice.model.Activity;
import com.fitme.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest request) {
        
        boolean isValidUser = userValidationService.validateUser(request.userId());

        if (!isValidUser) {
            throw new UserNotFoundException("User not found");
        }

        Activity saveActivity = activityRepository.save(activityMapper.toActivity(request));

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, saveActivity);
        } catch (Exception e) {
            log.error("Failed to send message to RabbitMQ: {}", e.getMessage());
        }
        
        return activityMapper.toActivityResponse(saveActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);

        return activities.stream()
            .map(activityMapper::toActivityResponse)
            .toList();
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityMapper.toActivityResponse(activityRepository.findById(activityId).orElseThrow());
    }
}
