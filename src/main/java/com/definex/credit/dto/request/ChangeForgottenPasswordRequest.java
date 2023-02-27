package com.definex.credit.dto.request;

import jakarta.validation.constraints.Size;


public record ChangeForgottenPasswordRequest(
        @Size(min = 8, max = 70, message = "{jakarta.validation.constraints.Size.Password.message}")
        String password) {
}