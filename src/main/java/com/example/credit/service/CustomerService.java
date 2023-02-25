package com.example.credit.service;

import com.example.credit.dto.request.CustomerUpdateRequest;
import com.example.credit.dto.response.CustomerResponse;
import com.example.credit.model.Auth;
import com.example.credit.model.Customer;
import com.example.credit.dto.request.CustomerRequest;

public interface CustomerService {
    Auth getAuth(String id);

    Customer saveCustomer(CustomerRequest customerRequest);

    Customer getCustomer(String customerId);

    CustomerResponse getCustomerByAuthId(String authId);

    void updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    Customer getCustomerByAuth(Auth auth);

    Customer getCustomerByIdentityNumber(String identityNumber);
}
