package com.definex.credit.service;

import com.definex.credit.dto.CreditRatingDto;
import com.definex.credit.model.CreditRating;

public interface CreditRatingService {
    void saveCurrentCreditRating(CreditRatingDto creditRatingDto);

    CreditRating getCreditRating();
}
