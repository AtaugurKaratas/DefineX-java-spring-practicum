package com.example.credit.repository;

import com.example.credit.model.Customer;
import com.example.credit.model.CustomerCreditRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerCreditRatingRepository extends JpaRepository<CustomerCreditRating, String> {
    Optional<CustomerCreditRating> getCustomerCreditRatingByCustomer(Customer customer);
}
