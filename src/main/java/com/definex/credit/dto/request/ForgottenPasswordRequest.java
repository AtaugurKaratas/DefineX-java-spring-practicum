package com.definex.credit.dto.request;

import jakarta.validation.constraints.Email;

public record ForgottenPasswordRequest(
        @Email
        String email) {
}