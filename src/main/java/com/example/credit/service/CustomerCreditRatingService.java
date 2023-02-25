package com.example.credit.service;

import com.example.credit.dto.request.CustomerCreditRatingRequest;
import com.example.credit.dto.response.CustomerCreditRatingResponse;
import com.example.credit.model.Customer;
import com.example.credit.model.CustomerCreditRating;

public interface CustomerCreditRatingService {
    void saveCustomerCreditRating(Customer customer);

    Customer getCustomer(String customerId);

    void updateCustomerCreditRating(CustomerCreditRatingRequest creditRatingRequest);

    CustomerCreditRating getCustomerCreditRatingByCustomer(Customer customer);

    CustomerCreditRatingResponse getCustomerCreditRating(String identityNumber);
}