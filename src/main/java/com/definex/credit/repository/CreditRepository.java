package com.definex.credit.repository;

import com.definex.credit.model.Credit;
import com.definex.credit.model.Customer;
import com.definex.credit.model.GuaranteeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, String> {
    //Credit getCreditByCustomer(Customer customer);

    Credit getCreditByGuaranteeCustomer(GuaranteeCustomer guaranteeCustomer);

    List<Credit> findCreditByCustomer(Customer customer);

    @Query("select u from Credit u where u.customer.id = ?1 order by u.createdDate desc limit 1 ")
    Credit findLastCreditRecordByCustomer(String customerId);
}
