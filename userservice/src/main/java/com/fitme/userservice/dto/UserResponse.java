package com.fitme.userservice.dto;

import java.time.LocalDateTime;

public record UserResponse(
    String id,
    String email,
    String firstName,
    String lastName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
