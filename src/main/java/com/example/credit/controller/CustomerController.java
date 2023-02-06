package com.example.credit.controller;

import com.example.credit.dto.request.CustomerRequest;
import com.example.credit.service.impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    private final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public void customerSave(@RequestBody CustomerRequest customerRequest) {
        logger.info("Logger " + customerRequest.getAuthId());
        customerService.saveCustomer(customerRequest);
    }
}
