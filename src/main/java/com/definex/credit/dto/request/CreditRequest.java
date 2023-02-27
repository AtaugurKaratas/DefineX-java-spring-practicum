package com.definex.credit.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CreditRequest(
        String customerId,
        String identityNumber,
        LocalDate birthDate,
        boolean guarantee,
        String guaranteeIdentityNumber,
        BigDecimal customerGuaranteePrice,
        String customerAssetId,
        BigDecimal assetGuaranteePrice) {
}