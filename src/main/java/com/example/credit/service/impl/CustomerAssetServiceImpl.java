package com.example.credit.service.impl;

import com.example.credit.dto.request.CustomerAssetRequest;
import com.example.credit.dto.response.CustomerAssetResponse;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Customer;
import com.example.credit.model.CustomerAsset;
import com.example.credit.model.enumeration.ImageType;
import com.example.credit.repository.CustomerAssetRepository;
import com.example.credit.service.CustomerAssetService;
import com.example.credit.util.ImageProcessing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerAssetServiceImpl implements CustomerAssetService {

    private final CustomerAssetRepository customerAssetRepository;

    private final CustomerServiceImpl customerService;

    private final ImageProcessing stringToImage;

    @Override
    public void saveCustomerAsset(CustomerAssetRequest customerAssetRequest) {
        String image;
        Customer customer = customerService.getCustomer(customerAssetRequest.customerId());
        CustomerAsset customerAsset = new CustomerAsset();
        customerAsset.setName(customerAssetRequest.assetName());
        customerAsset.setPrice(customerAssetRequest.price());
        if(customerAssetRequest.imagePath() != null) {
            image = stringToImage.imageProcess(customerAssetRequest.imagePath(), ImageType.ASSET);
        } else {
            image = "house.jpeg";
        }
        customerAsset.setImagePath(image);
        customerAsset.setCustomer(customer);
        customerAssetRepository.save(customerAsset);
    }

    @Override
    public List<CustomerAssetResponse> getCustomerAssets(String customerId) {
        List<CustomerAssetResponse> customerAssetResponses = new ArrayList<>();
        List<CustomerAsset> customerAssets = customerAssetRepository.findByCustomerId(customerId);
        for (CustomerAsset customerAsset : customerAssets) {
            CustomerAssetResponse customerAssetResponse = new CustomerAssetResponse(customerAsset);
            customerAssetResponses.add(customerAssetResponse);
        }
        return customerAssetResponses;
    }

    @Override
    public CustomerAsset getCustomerAsset(String customerAssetId) {
        return customerAssetRepository.findById(customerAssetId).orElseThrow(() -> {
            log.warn("getCustomerAsset - Customer Asset Not Found");
            return new NotFoundException("Customer Asset Not Found");
        });
    }
}