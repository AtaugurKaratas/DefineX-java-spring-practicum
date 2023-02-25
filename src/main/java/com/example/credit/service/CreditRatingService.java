package com.example.credit.service;

import com.example.credit.dto.CreditRatingDto;
import com.example.credit.model.CreditRating;

public interface CreditRatingService {
    void saveCurrentCreditRating(CreditRatingDto creditRatingDto);

    CreditRating getCreditRating();
}
