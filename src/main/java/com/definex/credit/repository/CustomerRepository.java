package com.definex.credit.repository;

import com.definex.credit.model.Auth;
import com.definex.credit.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> getCustomerByAuthId(String authId);

    Optional<Customer> getCustomerByAuth(Auth auth);

}
