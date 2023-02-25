package com.example.credit.controller;

import com.example.credit.dto.request.CustomerAssetRequest;
import com.example.credit.dto.response.CustomerAssetResponse;
import com.example.credit.service.impl.CustomerAssetServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/asset")
public class CustomerAssetController {

    private final CustomerAssetServiceImpl customerAssetService;

    public CustomerAssetController(CustomerAssetServiceImpl customerAssetService) {
        this.customerAssetService = customerAssetService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<String> saveCustomerAsset(@Valid @RequestBody CustomerAssetRequest customerAssetRequest){
        customerAssetService.saveCustomerAsset(customerAssetRequest);
        return new ResponseEntity<>("Asset Created", HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public List<CustomerAssetResponse> getCustomerAssets(@PathVariable String customerId){
        return customerAssetService.getCustomerAssets(customerId);
    }

}
