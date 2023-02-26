package com.example.credit.service.impl;

import com.example.credit.dto.CreditResultDto;
import com.example.credit.dto.request.CreditCalculationRequest;
import com.example.credit.dto.request.CreditRequest;
import com.example.credit.dto.request.CreditResultRequest;
import com.example.credit.dto.response.CreditCalculationResponse;
import com.example.credit.dto.response.CreditListResponse;
import com.example.credit.dto.response.CreditResultResponse;
import com.example.credit.exception.MismatchException;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Credit;
import com.example.credit.model.CreditRating;
import com.example.credit.model.Customer;
import com.example.credit.model.CustomerCreditRating;
import com.example.credit.model.enumeration.CreditProcess;
import com.example.credit.model.enumeration.GuaranteeType;
import com.example.credit.repository.CreditRepository;
import com.example.credit.service.CreditService;
import com.example.credit.util.SendSms;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    private final CustomerServiceImpl customerService;

    private final CreditRatingServiceImpl creditRatingService;

    private final CustomerCreditRatingServiceImpl customerCreditRatingService;

    private final GuaranteeAssetServiceImpl guaranteeAssetService;

    private final GuaranteeCustomerServiceImpl guaranteeCustomerService;

    private final SendSms sendSms;

    private static final double creditLimitMultiplier = 4;

    @Transactional
    @Override
    public void saveCredit(CreditRequest creditRequest) {
        Credit credit = new Credit();
        Customer customer = customerService.getCustomer(creditRequest.customerId());
        credit.setCustomer(customer);

        boolean checkIdentityNumber = customer.getAuth().getIdentityNumber().equals(creditRequest.identityNumber());
        boolean checkBirthDate = customer.getBirthDate().equals(creditRequest.birthDate());

        if (checkIdentityNumber && checkBirthDate) {
            log.info("Identity Number: {}, Birth Date: {} - Credit Request"
                    , creditRequest.identityNumber(), creditRequest.birthDate());
            if (creditRequest.guarantee()) {

                credit.setGuaranteeCheck(true);

                if (creditRequest.guaranteeIdentityNumber() != null) {

                    credit = saveGuaranteeCustomer(credit, creditRequest);

                } else if (creditRequest.customerAssetId() != null) {
                    credit = saveGuaranteeAsset(credit, creditRequest);
                }

            }
        } else {
            log.warn("Identity Number: {}, Birth Date: {} - Identity Number And Date Of Birth Do Not Match"
                    , creditRequest.identityNumber(), creditRequest.birthDate());
            throw new MismatchException("Identity Number And Date Of Birth Do Not Match");
        }

        creditRepository.save(credit);
    }

    @Override
    public Credit saveGuaranteeCustomer(Credit credit, CreditRequest creditRequest) {
        credit.setGuaranteeCustomerCheck(true);
        credit.setGuaranteeType(GuaranteeType.GUARANTEE_CUSTOMER);
        credit.setGuaranteeCustomer(
                guaranteeCustomerService.saveGuaranteeCustomer(
                        creditRequest.guaranteeIdentityNumber(),
                        creditRequest.customerGuaranteePrice(),
                        credit));
        return credit;
    }

    @Override
    public Credit saveGuaranteeAsset(Credit credit, CreditRequest creditRequest) {
        credit.setGuaranteeAssetCheck(true);
        credit.setGuaranteeType(GuaranteeType.GUARANTEE_ASSET);
        credit.setGuaranteeAsset(
                guaranteeAssetService.saveGuaranteeAsset(
                        creditRequest.customerAssetId(),
                        creditRequest.assetGuaranteePrice(),
                        credit));
        return credit;
    }

    @Override
    public double calculateCreditScoreCustomer(CreditRating creditRating, CustomerCreditRating customerCreditRating) {
        double creditScore = creditRating.getCreditScoreStartingValue();
        creditScore += (creditRating.getCreditUsageIntensity() * customerCreditRating.getCreditUsageIntensityScore()) / 10;
        creditScore += (creditRating.getNewCreditProductLaunches() * customerCreditRating.getNewCreditProductLaunchesScore()) / 10;
        creditScore += (creditRating.getCreditProductPaymentHabits() * customerCreditRating.getCreditProductPaymentHabitScore()) / 10;
        creditScore += (creditRating.getCurrentAccountAndDebitStatus() * customerCreditRating.getCurrentAccountAndDebitStatusScore()) / 10;
        return creditScore;
    }


    @Override
    public CreditCalculationResponse calculateCreditScore(CreditCalculationRequest creditCalculationRequest) {
        Credit credit = creditRepository.findById(creditCalculationRequest.creditId()).orElseThrow(() -> {
            log.warn("Credit Id: {} - Credit Application Not Found", creditCalculationRequest.creditId());
            return new NotFoundException("Credit Application Not Found");
        });
        CreditRating creditRating = creditRatingService.getCreditRating();
        CustomerCreditRating customerCreditRating = customerCreditRatingService
                .getCustomerCreditRatingByCustomer(credit.getCustomer());

        double creditScore = calculateCreditScoreCustomer(creditRating, customerCreditRating);
        BigDecimal creditLimit = calculateCreditLimit(credit, creditScore, creditLimitMultiplier);

        credit.setCreditScore(creditScore);
        credit.setPrice(creditLimit);
        credit.setCheck(creditScore >= 500);
        credit.setResponseTime(LocalDate.now());
        if (creditScore >= 500)
            sendSms.sendSuccessfulCreditResult(new CreditResultDto(credit));
        else
            sendSms.sendUnsuccessfulCreditResult(new CreditResultDto(credit));

        creditRepository.save(credit);

        return new CreditCalculationResponse(creditScore, BigDecimal.valueOf(creditLimit.doubleValue()));
    }

    @Override
    public BigDecimal calculateCreditLimit(Credit credit, double creditScore, double creditLimitMultiplier) {
        BigDecimal creditLimit = BigDecimal.ZERO;

        if (creditScore < 500) {
            creditScoreBelow500(credit);

        } else if (creditScore >= 500 && creditScore < 1000) {

            if (credit.getCustomer().getMonthlySalary().doubleValue() < 5000)
                creditLimit = creditScoreBetween500And1000AndMonthlySalaryBelow5000(credit);

            else if (credit.getCustomer().getMonthlySalary().doubleValue() < 10000)
                creditLimit = creditScoreBetween500And1000AndMonthlySalaryBetween5000And10000(credit);

            else
                creditLimit = creditScoreBetween500And1000AndMonthlySalaryAbove10000(credit, creditLimitMultiplier);

        } else if (creditScore >= 1000)
            creditLimit = creditScoreAbove1000(credit, creditLimitMultiplier);

        return creditLimit;
    }

    @Override
    public List<CreditListResponse> getAllCredits() {
        List<CreditListResponse> creditListResponses = new ArrayList<>();
        for (Credit credit : creditRepository.findAll()) {
            if (credit.getResponseTime() != null) continue;
            GuaranteeType guaranteeType = null;
            BigDecimal guaranteeAmount = BigDecimal.ZERO;
            if (credit.isGuaranteeCheck()) {
                if (credit.isGuaranteeCustomerCheck()) {
                    guaranteeType = credit.getGuaranteeType();
                    guaranteeAmount = credit.getGuaranteeCustomer().getGuaranteeAmount();
                    if (!credit.getGuaranteeCustomer().isApproval())
                        continue;
                } else if (credit.isGuaranteeAssetCheck()) {
                    guaranteeType = credit.getGuaranteeType();
                    guaranteeAmount = credit.getGuaranteeAsset().getGuaranteeAmount();
                }
            }
            CreditListResponse creditListResponse = new CreditListResponse(credit, guaranteeType, guaranteeAmount);
            creditListResponses.add(creditListResponse);
        }
        return creditListResponses;
    }

    @Override
    public CreditListResponse getCredit(String creditId) {
        Credit credit = creditRepository.findById(creditId).orElseThrow(() -> {
            log.warn("Credit Id: {} - Credit Not Found", creditId);
            return new NotFoundException("Credit Not Found");
        });
        GuaranteeType guaranteeType = null;
        BigDecimal guaranteePrice = BigDecimal.ZERO;
        if (credit.isGuaranteeCheck()) {
            guaranteeType = credit.getGuaranteeType();
            if (credit.isGuaranteeCustomerCheck()) {
                guaranteePrice = credit.getGuaranteeCustomer().getGuaranteeAmount();
            } else if (credit.isGuaranteeAssetCheck()) {
                guaranteePrice = credit.getGuaranteeAsset().getGuaranteeAmount();
            }
        }
        return new CreditListResponse(credit, guaranteeType, guaranteePrice);
    }

    @Override
    public void creditScoreBelow500(Credit credit) {
        credit.setCheck(true);
        credit.setResponseTime(LocalDate.now());
        creditRepository.save(credit);
    }

    @Override
    public List<Credit> getAllCreditByCustomer(Customer customer) {
        return creditRepository.findCreditByCustomer(customer);
    }

    @Override
    public BigDecimal creditScoreBetween500And1000AndMonthlySalaryBelow5000(Credit credit) {
        double price = 0;
        if (credit.isGuaranteeCheck()) {

            if (credit.isGuaranteeAssetCheck())
                price = 10000 + (credit.getGuaranteeAsset().getGuaranteeAmount().doubleValue() * 0.1);

            else if (credit.isGuaranteeCustomerCheck())
                price = 10000 + (credit.getGuaranteeCustomer().getGuaranteeAmount().doubleValue() * 0.1);

        } else
            price = 10000;

        credit.setCheck(true);
        credit.setResponseTime(LocalDate.now());
        credit.setPrice(BigDecimal.valueOf(price));
        creditRepository.save(credit);
        return credit.getPrice();
    }

    @Override
    public BigDecimal creditScoreBetween500And1000AndMonthlySalaryBetween5000And10000(Credit credit) {
        double price = 0;
        if (credit.isGuaranteeCheck()) {

            if (credit.isGuaranteeAssetCheck())
                price = 20000 + (credit.getGuaranteeAsset().getGuaranteeAmount().doubleValue() * 0.2);

            else if (credit.isGuaranteeCustomerCheck())
                price = 20000 + (credit.getGuaranteeCustomer().getGuaranteeAmount().doubleValue() * 0.2);

        } else
            price = 20000;

        credit.setCheck(true);
        credit.setResponseTime(LocalDate.now());
        credit.setPrice(BigDecimal.valueOf(price));
        creditRepository.save(credit);
        return credit.getPrice();
    }

    @Override
    public BigDecimal creditScoreBetween500And1000AndMonthlySalaryAbove10000(Credit credit, double creditLimitMultiplier) {
        double price = 0;
        if (credit.isGuaranteeCheck()) {

            if (credit.isGuaranteeAssetCheck())
                price += (credit.getGuaranteeAsset().getGuaranteeAmount().doubleValue() * 0.25);

            else if (credit.isGuaranteeCustomerCheck())
                price += (credit.getGuaranteeCustomer().getGuaranteeAmount().doubleValue() * 0.25);

        }
        price += (creditLimitMultiplier / 2) * credit.getCustomer().getMonthlySalary().doubleValue();

        credit.setCheck(true);
        credit.setResponseTime(LocalDate.now());
        credit.setPrice(BigDecimal.valueOf(price));
        creditRepository.save(credit);
        return credit.getPrice();
    }

    @Override
    public BigDecimal creditScoreAbove1000(Credit credit, double creditLimitMultiplier) {
        double price = 0;
        if (credit.isGuaranteeCheck()) {

            if (credit.isGuaranteeAssetCheck())
                price += (credit.getGuaranteeAsset().getGuaranteeAmount().doubleValue() * 0.50);

            else if (credit.isGuaranteeCustomerCheck())
                price += (credit.getGuaranteeCustomer().getGuaranteeAmount().doubleValue() * 0.50);

        }
        price += creditLimitMultiplier * credit.getCustomer().getMonthlySalary().doubleValue();

        credit.setCheck(true);
        credit.setResponseTime(LocalDate.now());
        credit.setPrice(BigDecimal.valueOf(price));
        creditRepository.save(credit);
        return credit.getPrice();
    }

    @Override
    public CreditResultResponse getCreditResult(CreditResultRequest creditResultRequest) {
        Customer customer = customerService.getCustomerByIdentityNumber(creditResultRequest.identityNumber());
        if (!customer.getBirthDate().equals(creditResultRequest.birthDate())) {
            log.warn("Identity Number: {}, Birth Date: {} - Identity Number And Date Of Birth Do Not Match"
                    , creditResultRequest.identityNumber(), creditResultRequest.birthDate());
            throw new MismatchException("Identity Number And Date Of Birth Do Not Match");
        }
        Credit credit = creditRepository.findLastCreditRecordByCustomer(customer.getId());
        return new CreditResultResponse(customer.getName()
                , customer.getSurname()
                , credit.getPrice()
                , credit.getResponseTime()
                , (credit.getResponseTime() == null ? CreditProcess.PENDING :
                (credit.isCheck() ? CreditProcess.CONFIRM : CreditProcess.REJECTION)));
    }
}