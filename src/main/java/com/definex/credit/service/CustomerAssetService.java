package com.definex.credit.service;

import com.definex.credit.dto.request.CustomerAssetRequest;
import com.definex.credit.dto.response.CustomerAssetResponse;
import com.definex.credit.model.CustomerAsset;

import java.util.List;

public interface CustomerAssetService {

    void saveCustomerAsset(CustomerAssetRequest customerAssetRequest);

    List<CustomerAssetResponse> getCustomerAssets(String customerId);

    CustomerAsset getCustomerAsset(String customerAssetId);
}
