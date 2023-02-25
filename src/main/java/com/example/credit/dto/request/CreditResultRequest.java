package com.example.credit.dto.request;

import java.time.LocalDate;
public record CreditResultRequest(
        String identityNumber,
        LocalDate birthDate) {
}