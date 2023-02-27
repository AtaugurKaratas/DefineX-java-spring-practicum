package com.definex.credit.repository;

import com.definex.credit.model.Customer;
import com.definex.credit.model.CustomerCreditRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerCreditRatingRepository extends JpaRepository<CustomerCreditRating, String> {
    Optional<CustomerCreditRating> getCustomerCreditRatingByCustomer(Customer customer);
}
