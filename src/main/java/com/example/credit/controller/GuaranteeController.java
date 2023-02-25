package com.example.credit.controller;


import com.example.credit.dto.request.UpdateGuaranteeRequest;
import com.example.credit.dto.response.GuaranteeResponse;
import com.example.credit.service.impl.GuaranteeAssetServiceImpl;
import com.example.credit.service.impl.GuaranteeCustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guarantee")
public class GuaranteeController {

    private final GuaranteeCustomerServiceImpl guaranteeCustomerService;

    private final GuaranteeAssetServiceImpl guaranteeAssetService;

    public GuaranteeController(GuaranteeCustomerServiceImpl guaranteeService, GuaranteeAssetServiceImpl guaranteeAssetService) {
        this.guaranteeCustomerService = guaranteeService;
        this.guaranteeAssetService = guaranteeAssetService;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<String> updateGuarantee(@RequestBody UpdateGuaranteeRequest updateGuaranteeRequest) {
        guaranteeCustomerService.updateGuarantee(updateGuaranteeRequest);
        return new ResponseEntity<>("Guarantee Updated", HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public List<GuaranteeResponse> getGuaranteeList(@PathVariable String customerId) {
        return guaranteeCustomerService.getGuaranteeList(customerId);
    }

    @GetMapping("/customer/{customerGuaranteeId}")
    public GuaranteeResponse getGuaranteeCustomer(@PathVariable String customerGuaranteeId){
        return guaranteeCustomerService.getGuarantee(customerGuaranteeId);
    }

    @GetMapping("/asset/{customerAssetId}")
    public GuaranteeResponse getGuaranteeAsset(@PathVariable String customerAssetId){
        return guaranteeAssetService.getGuarantee(customerAssetId);
    }

    @GetMapping("/result/{customerId}")
    public List<GuaranteeResponse> getGuaranteeResults(@PathVariable String customerId) {
        return guaranteeCustomerService.getGuaranteeResults(customerId);
    }
}