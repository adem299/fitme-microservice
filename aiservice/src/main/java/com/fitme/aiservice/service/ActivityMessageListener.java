package com.fitme.aiservice.service;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fitme.aiservice.dto.ActivityMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity (ActivityMessage message) {
        log.info("Received activity for processing: {}", message.id());
        try {
            log.info("Generate Recommendation: {}", activityAiService.generateRecommendation(message));
        } catch (Exception exception) {
            log.error("Failed to generate recommendation for activity {}: {}",
                    message.id(), exception.getMessage());
            throw new AmqpRejectAndDontRequeueException(
                    "Recommendation generation failed for activity " + message.id(), exception);
        }
    }
    
}
