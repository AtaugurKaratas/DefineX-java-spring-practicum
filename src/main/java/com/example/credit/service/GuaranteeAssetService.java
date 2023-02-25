package com.example.credit.service;

import com.example.credit.dto.response.GuaranteeResponse;
import com.example.credit.model.Credit;
import com.example.credit.model.GuaranteeAsset;

import java.math.BigDecimal;

public interface GuaranteeAssetService {

    GuaranteeAsset saveGuaranteeAsset(String customerAssetId, BigDecimal guaranteePrice, Credit credit);

    GuaranteeResponse getGuarantee(String guaranteeId);

    GuaranteeAsset getGuaranteeAsset(String guaranteeAssetId);
}
