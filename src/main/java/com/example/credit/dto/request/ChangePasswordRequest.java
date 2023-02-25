package com.example.credit.dto.request;

import jakarta.validation.constraints.Size;


public record ChangePasswordRequest(
        String authId,
        String password,
        @Size(min = 8, max = 70, message = "{jakarta.validation.constraints.Size.Password.message}")
        String newPassword) {
}