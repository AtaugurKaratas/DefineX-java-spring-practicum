package com.example.credit.dto.response;

import com.example.credit.model.CustomerCreditRating;

public record CustomerCreditRatingResponse(
        String id,
        double creditProductPaymentHabitScore,
        double currentAccountAndDebitStatusScore,
        double newCreditProductLaunchesScore,
        double creditUsageIntensityScore,
        String customerName,
        String customerSurname) {
    public CustomerCreditRatingResponse(CustomerCreditRating customerCreditRating){
        this(customerCreditRating.getId()
                , customerCreditRating.getCreditProductPaymentHabitScore()
                , customerCreditRating.getCurrentAccountAndDebitStatusScore()
                , customerCreditRating.getNewCreditProductLaunchesScore()
                , customerCreditRating.getCreditUsageIntensityScore()
                ,customerCreditRating.getCustomer().getName()
                ,customerCreditRating.getCustomer().getSurname());
    }
}