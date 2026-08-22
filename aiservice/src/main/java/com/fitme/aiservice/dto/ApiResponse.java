package com.fitme.aiservice.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record ApiResponse<T> (
    boolean success,
    String message,
    T data,
    LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(true, message, data, LocalDateTime.now(ZoneId.of("Asia/Jakarta")));
    }
}
