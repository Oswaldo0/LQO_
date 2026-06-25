package com.lqo.modules.auth.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username must have 100 characters or less")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
}
