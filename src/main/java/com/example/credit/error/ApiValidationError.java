package com.example.credit.error;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class ApiValidationError {
    private int status;

    private String message;

    private Instant timestamp = Instant.now();

    private Map<String, String> validationErrors;

    public ApiValidationError(int status, String message) {
        this.status = status;
        this.message = message;
    }

}