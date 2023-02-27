package com.definex.credit.dto.response;

import com.definex.credit.model.enumeration.CreditProcess;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditResultResponse(
        String name,
        String surname,
        BigDecimal limitValue,
        LocalDate responseDate,
        @Enumerated(EnumType.STRING)
        CreditProcess creditProcess) {
}