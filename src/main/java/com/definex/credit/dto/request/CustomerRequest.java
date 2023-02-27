package com.definex.credit.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CustomerRequest(
        @NotEmpty
        String customerName,
        @NotEmpty
        String customerSurname,
        @NotEmpty
        String phoneNumber,
        @Positive
        @NotNull
        BigDecimal monthlySalary,
        String imagePath,
        @Past
        LocalDate birthDate,
        String authId) {
}