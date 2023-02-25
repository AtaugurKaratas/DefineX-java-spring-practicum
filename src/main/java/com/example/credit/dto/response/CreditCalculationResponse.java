package com.example.credit.dto.response;

import java.math.BigDecimal;


public record CreditCalculationResponse(
        double creditRating,
        BigDecimal creditLimitValue) {
}