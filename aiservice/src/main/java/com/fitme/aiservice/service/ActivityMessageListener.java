package com.fitme.aiservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fitme.aiservice.dto.ActivityMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    @RabbitListener(queues = "${activity.queue}")
    public void processActivity (ActivityMessage message) {
        log.info("Received activity for processing: {}", message);
    }
    
}
