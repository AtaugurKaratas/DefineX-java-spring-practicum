package com.definex.credit.dto;

import com.definex.credit.model.CreditRating;
import jakarta.validation.constraints.Positive;

public record CreditRatingDto(
        @Positive
        double creditProductPaymentHabits,
        @Positive
        double currentAccountAndDebitStatus,
        @Positive
        double newCreditProductLaunches,
        @Positive
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