package com.definex.credit.service;

import com.definex.credit.dto.response.GuaranteeResponse;
import com.definex.credit.model.Credit;
import com.definex.credit.model.GuaranteeAsset;

import java.math.BigDecimal;

public interface GuaranteeAssetService {

    GuaranteeAsset saveGuaranteeAsset(String customerAssetId, BigDecimal guaranteePrice, Credit credit);

    GuaranteeResponse getGuarantee(String guaranteeId);

    GuaranteeAsset getGuaranteeAsset(String guaranteeAssetId);
}
