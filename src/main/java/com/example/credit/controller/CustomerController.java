package com.example.credit.controller;

import com.example.credit.dto.request.CustomerRequest;
import com.example.credit.dto.request.CustomerUpdateRequest;
import com.example.credit.dto.response.CustomerResponse;
import com.example.credit.model.Customer;
import com.example.credit.service.impl.CustomerCreditRatingServiceImpl;
import com.example.credit.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    private final CustomerCreditRatingServiceImpl customerCreditRatingService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<String> customerSave(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.saveCustomer(customerRequest);
        customerCreditRatingService.saveCustomerCreditRating(customer);
        return new ResponseEntity<>("Customer Created", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> customerUpdate(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerUpdateRequest);
        return new ResponseEntity<>("Customer Updated", HttpStatus.OK);
    }

    @GetMapping("/{authId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public CustomerResponse getCustomer(@PathVariable String authId) {
        return customerService.getCustomerByAuthId(authId);
    }
}