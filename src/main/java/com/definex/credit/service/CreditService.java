package com.definex.credit.service;

import com.definex.credit.dto.request.CreditCalculationRequest;
import com.definex.credit.dto.request.CreditRequest;
import com.definex.credit.dto.request.CreditResultRequest;
import com.definex.credit.dto.response.CreditCalculationResponse;
import com.definex.credit.dto.response.CreditListResponse;
import com.definex.credit.dto.response.CreditResultResponse;
import com.definex.credit.model.Credit;
import com.definex.credit.model.CreditRating;
import com.definex.credit.model.Customer;
import com.definex.credit.model.CustomerCreditRating;

import java.math.BigDecimal;
import java.util.List;

public interface CreditService {
    void saveCredit(CreditRequest creditRequest);

    Credit saveGuaranteeCustomer(Credit credit, CreditRequest creditRequest);

    Credit saveGuaranteeAsset(Credit credit, CreditRequest creditRequest);

    double calculateCreditScoreCustomer(CreditRating creditRating, CustomerCreditRating customerCreditRating);

    CreditCalculationResponse calculateCreditScore(CreditCalculationRequest creditCalculationRequest);

    BigDecimal calculateCreditLimit(Credit credit, double creditScore, double creditLimitMultiplier);

    List<CreditListResponse> getAllCredits();

    CreditListResponse getCredit(String creditId);

    void creditScoreBelow500(Credit credit);

    List<Credit> getAllCreditByCustomer(Customer customer);

    BigDecimal creditScoreBetween500And1000AndMonthlySalaryBelow5000(Credit credit);

    BigDecimal creditScoreBetween500And1000AndMonthlySalaryBetween5000And10000(Credit credit);

    BigDecimal creditScoreBetween500And1000AndMonthlySalaryAbove10000(Credit credit, double creditLimitMultiplier);

    BigDecimal creditScoreAbove1000(Credit credit, double creditLimitMultiplier);

    CreditResultResponse getCreditResult(CreditResultRequest creditResultRequest);
}