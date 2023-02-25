package com.example.credit.repository;

import com.example.credit.model.Customer;
import com.example.credit.model.GuaranteeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuaranteeCustomerRepository extends JpaRepository<GuaranteeCustomer, String>{

    List<GuaranteeCustomer> findByCustomer(Customer customer);
}
