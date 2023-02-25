package com.example.credit.repository;

import com.example.credit.model.Credit;
import com.example.credit.model.CreditRating;
import com.example.credit.model.Customer;
import com.example.credit.model.GuaranteeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, String> {
    //Credit getCreditByCustomer(Customer customer);

    Credit getCreditByGuaranteeCustomer(GuaranteeCustomer guaranteeCustomer);

    List<Credit> findCreditByCustomer(Customer customer);

    @Query("select u from Credit u where u.customer.id = ?1 order by u.createdDate desc limit 1 ")
    Credit findLastCreditRecordByCustomer(String customerId);
}
