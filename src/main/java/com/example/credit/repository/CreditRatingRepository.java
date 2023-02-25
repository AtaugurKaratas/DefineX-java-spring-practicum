package com.example.credit.repository;

import com.example.credit.model.CreditRating;
import com.example.credit.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CreditRatingRepository extends JpaRepository<CreditRating, String> {
    @Query("select u from CreditRating u order by u.createdDate desc limit 1")
    CreditRating findLastRecord();
}
