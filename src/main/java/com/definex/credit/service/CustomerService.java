package com.definex.credit.service;

import com.definex.credit.dto.request.CustomerUpdateRequest;
import com.definex.credit.dto.response.CustomerResponse;
import com.definex.credit.model.Auth;
import com.definex.credit.model.Customer;
import com.definex.credit.dto.request.CustomerRequest;

public interface CustomerService {
    Auth getAuth(String id);

    Customer saveCustomer(CustomerRequest customerRequest);

    Customer getCustomer(String customerId);

    CustomerResponse getCustomerByAuthId(String authId);

    void updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    Customer getCustomerByAuth(Auth auth);

    Customer getCustomerByIdentityNumber(String identityNumber);
}
