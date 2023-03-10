package com.definex.credit.dto.response;

import com.definex.credit.model.CustomerAsset;

import java.math.BigDecimal;

public record CustomerAssetResponse(
        String id,
        String name,
        BigDecimal price,
        String imagePath) {
    public CustomerAssetResponse(CustomerAsset customerAsset){
        this(customerAsset.getId()
                , customerAsset.getName()
                , customerAsset.getPrice()
                , "/images/" +customerAsset.getImagePath());
    }
}