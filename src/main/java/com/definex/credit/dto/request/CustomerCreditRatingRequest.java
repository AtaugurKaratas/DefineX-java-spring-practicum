package com.definex.credit.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CustomerCreditRatingRequest(
    String id,
    @Min(value = 0)
    @Max(value = 100)
    double creditProductPaymentHabitScore,
    @Min(value = 0)
    @Max(value = 100)
    double currentAccountAndDebitStatusScore,
    @Min(value = 0)
    @Max(value = 100)
    double newCreditProductLaunchesScore,
    @Min(value = 0)
    @Max(value = 100)
    double creditUsageIntensityScore){
}