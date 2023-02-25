package com.example.credit.dto.response;

public record AuthTokenResponse(
        String id,
        String token,
        String role) {
}