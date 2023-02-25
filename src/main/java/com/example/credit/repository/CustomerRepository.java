package com.example.credit.repository;

import com.example.credit.model.Auth;
import com.example.credit.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> getCustomerByAuthId(String authId);

    Optional<Customer> getCustomerByAuth(Auth auth);

}
