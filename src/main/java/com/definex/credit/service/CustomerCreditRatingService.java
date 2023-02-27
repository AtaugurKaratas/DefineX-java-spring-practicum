package com.definex.credit.service;

import com.definex.credit.dto.request.CustomerCreditRatingRequest;
import com.definex.credit.model.Customer;
import com.definex.credit.dto.response.CustomerCreditRatingResponse;
import com.definex.credit.model.CustomerCreditRating;

public interface CustomerCreditRatingService {
    void saveCustomerCreditRating(Customer customer);

    Customer getCustomer(String customerId);

    void updateCustomerCreditRating(CustomerCreditRatingRequest creditRatingRequest);

    CustomerCreditRating getCustomerCreditRatingByCustomer(Customer customer);

    CustomerCreditRatingResponse getCustomerCreditRating(String identityNumber);
}