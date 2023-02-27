package com.definex.credit.repository;

import com.definex.credit.model.Customer;
import com.definex.credit.model.GuaranteeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuaranteeCustomerRepository extends JpaRepository<GuaranteeCustomer, String>{

    List<GuaranteeCustomer> findByCustomer(Customer customer);
}
