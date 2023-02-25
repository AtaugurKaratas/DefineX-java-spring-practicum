package com.example.credit.dto.request;

import jakarta.validation.constraints.NotNull;


public record AuthLoginRequest(
        @NotNull String identityNumber,
        @NotNull String password) {
}