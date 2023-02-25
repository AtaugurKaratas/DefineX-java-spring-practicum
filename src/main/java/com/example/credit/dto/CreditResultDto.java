package com.example.credit.dto;

import com.example.credit.model.Credit;

import java.math.BigDecimal;

public record CreditResultDto(
        String phoneNumber,
        String customerName,
        String customerSurname,
        BigDecimal creditLimit) {
    public CreditResultDto(Credit credit) {
        this(credit.getCustomer().getPhoneNumber()
                , credit.getCustomer().getName()
                , credit.getCustomer().getSurname(),
                credit.getPrice());
    }
}