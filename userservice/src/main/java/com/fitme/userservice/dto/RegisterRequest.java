package com.fitme.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    String email,

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters")
    String password,
    String firstName,
    String lastName
) {
    public RegisterRequest(String email, String password) {
        this(email, password, null, null);
    }
}
