package com.fitme.aiservice.service;

import org.springframework.stereotype.Service;

import com.fitme.aiservice.dto.ActivityMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final GroqService groqService;

    public String generateRecommendation(ActivityMessage activityMessage) {
        String prompt = createActivityPrompt(activityMessage);
        String aiResponse = groqService.getAnswer(prompt);
        log.info("AI response: {}", aiResponse);
        return aiResponse;
    }

    private String createActivityPrompt(ActivityMessage activityMessage) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
            "analysis": {
                "overall": "Overall analysis here",
                "pace": "Pace analysis here",
                "heartRate": "Heart rate analysis here",
                "caloriesBurned": "Calories analysis here"
            },
            "improvements": [
                {
                "area": "Area name",
                "recommendation": "Detailed recommendation"
                }
            ],
            "suggestions": [
                {
                "workout": "Workout name",
                "description": "Detailed workout description"
                }
            ],
            "safety": [
                "Safety point 1",
                "Safety point 2"
            ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activityMessage.activityType(),
                activityMessage.duration(),
                activityMessage.caloriesBurned(),
                activityMessage.additionalMetrics()
        );
    }
}
