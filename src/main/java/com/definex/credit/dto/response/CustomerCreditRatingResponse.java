package com.definex.credit.dto.response;

import com.definex.credit.model.CustomerCreditRating;

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