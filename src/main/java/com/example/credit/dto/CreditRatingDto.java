package com.example.credit.dto;

import com.example.credit.model.CreditRating;

public record CreditRatingDto(
        double creditProductPaymentHabits,
        double currentAccountAndDebitStatus,
        double newCreditProductLaunches,
        double creditUsageIntensity,
        int creditScoreStartingValue) {

    public CreditRatingDto(CreditRating creditRating) {
        this(creditRating.getCreditProductPaymentHabits()
                , creditRating.getCurrentAccountAndDebitStatus()
                , creditRating.getNewCreditProductLaunches()
                , creditRating.getCreditUsageIntensity()
                , creditRating.getCreditScoreStartingValue());
    }
}