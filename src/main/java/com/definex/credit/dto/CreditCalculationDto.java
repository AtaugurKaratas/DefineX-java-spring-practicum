package com.definex.credit.dto;

import java.math.BigDecimal;

public record CreditCalculationDto(
        double creditScore,
        BigDecimal creditLimit) {
}