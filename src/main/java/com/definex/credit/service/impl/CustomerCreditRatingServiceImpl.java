package com.definex.credit.service.impl;

import com.definex.credit.dto.request.CustomerCreditRatingRequest;
import com.definex.credit.model.Customer;
import com.definex.credit.dto.response.CustomerCreditRatingResponse;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.CustomerCreditRating;
import com.definex.credit.repository.CustomerCreditRatingRepository;
import com.definex.credit.service.CustomerCreditRatingService;
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
        CustomerCreditRating customerCreditRating = customerCreditRatingRepository
                .findById(customerCreditRatingRequest.id()).orElseThrow(() -> {
                    log.warn("Customer Credit Rating Id: {} - Customer Credit Rating Not Found", customerCreditRatingRequest.id());
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
            log.warn("Customer : {} - Customer Credit Rating Not Found", customer);
            return new NotFoundException("Customer Credit Rating Not Found");
        });
    }

    @Override
    public CustomerCreditRatingResponse getCustomerCreditRating(String identityNumber) {
        Customer customer = customerService.getCustomerByIdentityNumber(identityNumber);
        CustomerCreditRating customerCreditRating = customerCreditRatingRepository
                .getCustomerCreditRatingByCustomer(customer).orElseThrow(() -> {
                    log.warn("Customer : {} - Customer Credit Rating Not Found", customer);
                    return new NotFoundException("Customer Credit Rating Not Found");
                });
        return new CustomerCreditRatingResponse(customerCreditRating);
    }
}