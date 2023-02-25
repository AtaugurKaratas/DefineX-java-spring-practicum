package com.example.credit.service.impl;

import com.example.credit.dto.request.CustomerCreditRatingRequest;
import com.example.credit.dto.response.CustomerCreditRatingResponse;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Customer;
import com.example.credit.model.CustomerCreditRating;
import com.example.credit.repository.CustomerCreditRatingRepository;
import com.example.credit.service.CustomerCreditRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerCreditRatingServiceImpl implements CustomerCreditRatingService {

    private final CustomerCreditRatingRepository customerCreditRatingRepository;

    private final CustomerServiceImpl customerService;

    @Override
    public void saveCustomerCreditRating(Customer customer) {
        CustomerCreditRating customerCreditRating = CustomerCreditRating.builder()
                .newCreditProductLaunchesScore(50)
                .creditUsageIntensityScore(50)
                .currentAccountAndDebitStatusScore(50)
                .creditProductPaymentHabitScore(50)
                .customer(customer).build();
        customerCreditRatingRepository.save(customerCreditRating);
    }

    @Override
    public Customer getCustomer(String customerId) {
        return customerService.getCustomer(customerId);
    }

    @Override
    public void updateCustomerCreditRating(CustomerCreditRatingRequest customerCreditRatingRequest) {
        CustomerCreditRating customerCreditRating = customerCreditRatingRepository.findById(customerCreditRatingRequest.id()).orElseThrow(() -> {
            log.warn("updateCustomerCreditRating - Customer Credit Rating Not Found");
            return new NotFoundException("Customer Credit Rating Not Found");
        });
        customerCreditRating.setCreditUsageIntensityScore(customerCreditRatingRequest.creditUsageIntensityScore());
        customerCreditRating.setNewCreditProductLaunchesScore(customerCreditRatingRequest.newCreditProductLaunchesScore());
        customerCreditRating.setCurrentAccountAndDebitStatusScore(customerCreditRatingRequest.currentAccountAndDebitStatusScore());
        customerCreditRating.setCreditProductPaymentHabitScore(customerCreditRatingRequest.creditProductPaymentHabitScore());
        customerCreditRatingRepository.save(customerCreditRating);
    }

    @Override
    public CustomerCreditRating getCustomerCreditRatingByCustomer(Customer customer) {
        return customerCreditRatingRepository.getCustomerCreditRatingByCustomer(customer).orElseThrow(() -> {
            log.warn("getCustomerCreditRatingByCustomer - Customer Credit Rating Not Found");
            return new NotFoundException("Customer Credit Rating Not Found");
        });
    }

    @Override
    public CustomerCreditRatingResponse getCustomerCreditRating(String identityNumber) {
        Customer customer = customerService.getCustomerByIdentityNumber(identityNumber);
        CustomerCreditRating customerCreditRating = customerCreditRatingRepository
                .getCustomerCreditRatingByCustomer(customer).orElseThrow(() -> {
                    log.warn("getCustomerCreditRating - Customer Credit Rating Not Found");
                    return new NotFoundException("Customer Credit Rating Not Found");
                });
        return new CustomerCreditRatingResponse(customerCreditRating);
    }
}