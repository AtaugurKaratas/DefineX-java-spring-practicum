package com.example.credit.dto.request;

public record CustomerCreditRatingRequest(
    String id,
    double creditProductPaymentHabitScore,
    double currentAccountAndDebitStatusScore,
    double newCreditProductLaunchesScore,
    double creditUsageIntensityScore){
}