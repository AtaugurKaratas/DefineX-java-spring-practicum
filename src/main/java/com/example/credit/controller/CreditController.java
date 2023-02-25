package com.example.credit.controller;

import com.example.credit.dto.CreditRatingDto;
import com.example.credit.dto.request.CreditCalculationRequest;
import com.example.credit.dto.request.CreditRequest;
import com.example.credit.dto.request.CreditResultRequest;
import com.example.credit.dto.request.CustomerCreditRatingRequest;
import com.example.credit.dto.response.CreditCalculationResponse;
import com.example.credit.dto.response.CreditListResponse;
import com.example.credit.dto.response.CreditResultResponse;
import com.example.credit.dto.response.CustomerCreditRatingResponse;
import com.example.credit.model.CreditRating;
import com.example.credit.service.impl.CreditRatingServiceImpl;
import com.example.credit.service.impl.CreditServiceImpl;
import com.example.credit.service.impl.CustomerCreditRatingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditServiceImpl creditService;

    private final CreditRatingServiceImpl creditRatingService;

    private final CustomerCreditRatingServiceImpl customerCreditRatingService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<String> creditSave(@RequestBody CreditRequest creditRequest) {
        creditService.saveCredit(creditRequest);
        return new ResponseEntity<>("Credit Saved", HttpStatus.CREATED);
    }

    @PostMapping("/credit-rating")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> currentCreditRating(@RequestBody CreditRatingDto creditRatingDto) {
        creditRatingService.saveCurrentCreditRating(creditRatingDto);
        return new ResponseEntity<>("Credit Rating Saved", HttpStatus.CREATED);
    }

    @PutMapping("/customer-credit-rating")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public ResponseEntity<String> updateCustomerCreditRating
            (@RequestBody CustomerCreditRatingRequest customerCreditRatingRequest) {
        customerCreditRatingService.updateCustomerCreditRating(customerCreditRatingRequest);
        return new ResponseEntity<>("Customer Credit Rating Updated", HttpStatus.OK);
    }

    @PutMapping("/calculation-credit")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public CreditCalculationResponse calculationCredit
            (@RequestBody CreditCalculationRequest creditCalculationRequest) {
        return creditService.calculateCreditScore(creditCalculationRequest);
    }

    @GetMapping("/calculation-credit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CreditRatingDto getCreditValues() {
        CreditRating creditRating = creditRatingService.getCreditRating();
        return new CreditRatingDto(creditRating);
    }

    @GetMapping("/credit-list")
    public List<CreditListResponse> getAllCredits() {
        return creditService.getAllCredits();
    }

    @GetMapping("/{creditId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_EMPLOYEE')")
    public CreditListResponse getCredit(@PathVariable String creditId) {
        return creditService.getCredit(creditId);
    }

    @PostMapping("/credit-result")
    public CreditResultResponse getCreditResult(@RequestBody CreditResultRequest creditResultRequest) {
        return creditService.getCreditResult(creditResultRequest);
    }

    @GetMapping("/customer-credit-rating/{identityNumber}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public CustomerCreditRatingResponse getCustomerCreditRating(@PathVariable String identityNumber) {
        return customerCreditRatingService.getCustomerCreditRating(identityNumber);
    }
}