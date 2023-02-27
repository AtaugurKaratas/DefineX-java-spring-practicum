package com.definex.credit.error;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ApiError {
    private int status;

    private String message;

    private Instant timestamp = Instant.now();

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

}