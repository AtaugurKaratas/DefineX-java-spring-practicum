package com.definex.credit.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record CustomerAssetRequest(
        @NotEmpty
        String assetName,
        @Positive
        @NotNull
        BigDecimal price,
        String imagePath,
        String customerId) {
}