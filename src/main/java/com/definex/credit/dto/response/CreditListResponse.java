package com.definex.credit.dto.response;

import com.definex.credit.model.Credit;
import com.definex.credit.model.enumeration.GuaranteeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record CreditListResponse(
        String id,
        String customerName,
        String customerSurname,
        BigDecimal monthlySalary,
        @Enumerated(EnumType.STRING)
        GuaranteeType guaranteeType,
        BigDecimal guaranteeAmount,
        String imagePath) {
    public CreditListResponse(Credit credit, GuaranteeType guaranteeType, BigDecimal guaranteeAmount) {
        this(credit.getId()
                , credit.getCustomer().getName()
                , credit.getCustomer().getSurname()
                , credit.getCustomer().getMonthlySalary()
                , guaranteeType
                , guaranteeAmount
                , ("/images/" +credit.getCustomer().getImagePath()));
    }
}