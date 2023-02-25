package com.example.credit.dto.request;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AdminInfoUpdateRequest(
        String adminId,
        String adminName,
        String adminSurname,
        String phoneNumber,
        @Past LocalDate birthDate,
        String imagePath) {
}