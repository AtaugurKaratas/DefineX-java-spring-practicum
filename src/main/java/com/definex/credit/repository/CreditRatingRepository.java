package com.definex.credit.repository;

import com.definex.credit.model.CreditRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreditRatingRepository extends JpaRepository<CreditRating, String> {
    @Query("select u from CreditRating u order by u.createdDate desc limit 1")
    CreditRating findLastRecord();
}
