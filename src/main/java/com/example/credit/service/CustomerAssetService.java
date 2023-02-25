package com.example.credit.service;

import com.example.credit.dto.request.CustomerAssetRequest;
import com.example.credit.dto.response.CustomerAssetResponse;
import com.example.credit.model.CustomerAsset;

import java.util.List;

public interface CustomerAssetService {

    void saveCustomerAsset(CustomerAssetRequest customerAssetRequest);

    List<CustomerAssetResponse> getCustomerAssets(String customerId);

    CustomerAsset getCustomerAsset(String customerAssetId);
}
