package com.fitme.aiservice.service;

import tools.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {
    private final WebClient groqWebClient;
    private final String groqApiKey;
    private final String groqApiUrl;
    private final int maxTokens;
    private final long requestIntervalMs;
    private long lastRequestAt;

    public GroqService(
            @Value("${groq.api.url}") String groqApiUrl,
            @Value("${groq.api.key}") String groqApiKey,
            @Value("${groq.api.max-tokens:800}") int maxTokens,
            @Value("${groq.api.request-interval-ms:2000}") long requestIntervalMs) {
        this.groqWebClient = WebClient.builder().build();
        this.groqApiUrl = groqApiUrl;
        this.groqApiKey = groqApiKey;
        this.maxTokens = maxTokens;
        this.requestIntervalMs = requestIntervalMs;
    }

    public String getAnswer(String questions) {
        if (questions == null || questions.isBlank()) {
            throw new IllegalArgumentException("Question must not be blank");
        }

        Map<String, Object> request = Map.of(
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", questions)),
            "model", "openai/gpt-oss-120b",
            "max_tokens", maxTokens);

        try {
            waitForRateLimit();
            JsonNode response = groqWebClient.post()
                    .uri(groqApiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            JsonNode answer = response == null
                    ? null
                    : response.path("choices").path(0).path("message").path("content");
            if (answer == null || answer.isMissingNode() || answer.isNull()) {
                throw new IllegalStateException("Groq response does not contain an answer");
            }
            return answer.asText();
        } catch (WebClientResponseException exception) {
            throw new IllegalStateException(
                "Groq request failed with status " + exception.getStatusCode()
                    + ": " + exception.getResponseBodyAsString(), exception);
        }
    }

    private synchronized void waitForRateLimit() {
        long waitTime = requestIntervalMs - (System.currentTimeMillis() - lastRequestAt);
        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while rate limiting Groq requests", exception);
            }
        }
        lastRequestAt = System.currentTimeMillis();
    }
}
