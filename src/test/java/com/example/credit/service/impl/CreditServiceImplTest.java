package com.example.credit.service.impl;

import com.example.credit.dto.request.CreditCalculationRequest;
import com.example.credit.dto.request.CreditRequest;
import com.example.credit.dto.request.CreditResultRequest;
import com.example.credit.dto.response.CreditListResponse;
import com.example.credit.exception.MismatchException;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.*;
import com.example.credit.model.enumeration.GuaranteeType;
import com.example.credit.repository.CreditRepository;
import com.example.credit.util.SendSms;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {


    @InjectMocks
    private CreditServiceImpl creditService;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private CreditRatingServiceImpl creditRatingService;

    @Mock
    private CustomerCreditRatingServiceImpl customerCreditRatingService;

    @Mock
    private GuaranteeAssetServiceImpl guaranteeAssetService;

    @Mock
    private GuaranteeCustomerServiceImpl guaranteeCustomerService;

    @Mock
    private SendSms sendSms;

    @Test
    void ShouldThrowMisMatchExceptionWhenBirthDatesAreNotMatches() {
        CreditRequest creditRequest = new CreditRequest("customerId"
                , "12345678901"
                , LocalDate.of(2000, 1, 1)
                , false
                , null
                , null
                , null
                , null);

        Auth auth = new Auth();
        auth.setIdentityNumber("12345678901");
        Customer customer = new Customer();
        customer.setAuth(auth);
        customer.setBirthDate(LocalDate.of(1998, 6, 4));

        when(customerService.getCustomer(creditRequest.customerId())).thenReturn(customer);

        MismatchException exception = assertThrows(MismatchException.class, () ->
                creditService.saveCredit(creditRequest));

        assertEquals("Identity Number And Date Of Birth Do Not Match", exception.getMessage());
    }

    @Test
    void ShouldSaveCreditWhenGuaranteeCustomerIsCheck() {
        CreditRequest creditRequest = new CreditRequest("customerId"
                , "12345678901"
                , LocalDate.of(1998, 6, 4)
                , true
                , "22222222222"
                , BigDecimal.valueOf(5000)
                , null
                , null);

        Auth auth = new Auth();
        auth.setIdentityNumber("12345678901");
        Customer customer = new Customer();
        customer.setAuth(auth);
        customer.setBirthDate(LocalDate.of(1998, 6, 4));

        when(customerService.getCustomer(creditRequest.customerId())).thenReturn(customer);

        creditService.saveCredit(creditRequest);

        verify(creditRepository).save(any(Credit.class));
    }

    @Test
    void ShouldSaveCreditWhenGuaranteeAssetIsCheck() {
        CreditRequest creditRequest = new CreditRequest("customerId"
                , "12345678901"
                , LocalDate.of(1998, 6, 4)
                , true
                , null
                , BigDecimal.valueOf(5000)
                , "customerAssetId"
                , BigDecimal.valueOf(5000));

        Auth auth = new Auth();
        auth.setIdentityNumber("12345678901");
        Customer customer = new Customer();
        customer.setAuth(auth);
        customer.setBirthDate(LocalDate.of(1998, 6, 4));

        when(customerService.getCustomer(creditRequest.customerId())).thenReturn(customer);

        creditService.saveCredit(creditRequest);

        verify(creditRepository).save(any(Credit.class));
    }

    @Test
    void ShouldSaveGuaranteeCustomer() {
        Credit credit = new Credit();
        credit.setGuaranteeCustomerCheck(true);
        credit.setGuaranteeType(GuaranteeType.GUARANTEE_CUSTOMER);

        CreditRequest creditRequest = new CreditRequest("customerId"
                , "12345678901"
                , LocalDate.of(1998, 6, 4)
                , true
                , "22222222222"
                , BigDecimal.valueOf(5000)
                , null
                , null);

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        credit.setGuaranteeCustomer(guaranteeCustomer);

        when(guaranteeCustomerService.saveGuaranteeCustomer(creditRequest.guaranteeIdentityNumber()
                , creditRequest.customerGuaranteePrice()
                , credit)).thenReturn(guaranteeCustomer);

        Credit creditResult = creditService.saveGuaranteeCustomer(credit, creditRequest);

        assertEquals(credit, creditResult);
    }

    @Test
    void ShouldSaveGuaranteeAsset() {
        Credit credit = new Credit();
        credit.setGuaranteeAssetCheck(true);
        credit.setGuaranteeType(GuaranteeType.GUARANTEE_ASSET);


        CreditRequest creditRequest = new CreditRequest("customerId"
                , "12345678901"
                , LocalDate.of(1998, 6, 4)
                , true
                , null
                , null
                , "customerAssetId"
                , BigDecimal.valueOf(5000));

        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        credit.setGuaranteeAsset(guaranteeAsset);

        when(guaranteeAssetService.saveGuaranteeAsset(creditRequest.customerAssetId()
                , creditRequest.assetGuaranteePrice()
                , credit)).thenReturn(guaranteeAsset);
        Credit creditResult = creditService.saveGuaranteeAsset(credit, creditRequest);

        assertEquals(credit, creditResult);
    }

    @Test
    void ShouldCalculateCreditScoreCustomer(){
        CreditRating creditRating = new CreditRating(250,25,25,25,25);
        Customer customer = mock(Customer.class);
        CustomerCreditRating customerCreditRating = new CustomerCreditRating(50,50,50,50,customer);
        double result = creditService.calculateCreditScoreCustomer(creditRating, customerCreditRating);
        assertEquals(750, result);
    }

    @Test
    void ShouldThrowNotFoundExceptionWhenCreditNotFound(){
        CreditCalculationRequest request =
                new CreditCalculationRequest("id");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                creditService.calculateCreditScore(request));

        assertEquals("Credit Application Not Found", exception.getMessage());
    }

    @Test
    void ShouldCalculationCreditResponseWhenCreditScoreEqualsOrBiggerThan500(){
        CreditCalculationRequest request =
                new CreditCalculationRequest("creditId");

        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(10000));
        Credit credit = new Credit();
        credit.setId("creditId");
        credit.setCreditScore(500);
        credit.setCustomer(customer);
        credit.setCheck(true);

        when(creditRepository.findById("creditId")).thenReturn(Optional.of(credit));

        CreditRating creditRating = new CreditRating();
        creditRating.setCreditScoreStartingValue(250);
        creditRating.setCreditUsageIntensity(25);
        creditRating.setNewCreditProductLaunches(25);
        creditRating.setCreditProductPaymentHabits(25);
        creditRating.setCurrentAccountAndDebitStatus(25);
        when(creditRatingService.getCreditRating()).thenReturn(creditRating);

        CustomerCreditRating customerCreditRating = new CustomerCreditRating();
        customerCreditRating.setCreditUsageIntensityScore(100);
        customerCreditRating.setCreditProductPaymentHabitScore(100);
        customerCreditRating.setCurrentAccountAndDebitStatusScore(100);
        customerCreditRating.setNewCreditProductLaunchesScore(100);
        when(customerCreditRatingService.getCustomerCreditRatingByCustomer(credit.getCustomer()))
                .thenReturn(customerCreditRating);

        creditService.calculateCreditScore(request);
    }

    @Test
    void ShouldCalculationCreditResponseWhenCreditScoreSmallerThan500(){
        CreditCalculationRequest request =
                new CreditCalculationRequest("creditId");

        Customer customer = new Customer();
        Credit credit = new Credit();
        credit.setId("creditId");
        credit.setCreditScore(300);
        credit.setCustomer(customer);

        when(creditRepository.findById("creditId")).thenReturn(Optional.of(credit));

        CreditRating creditRating = new CreditRating();
        when(creditRatingService.getCreditRating()).thenReturn(creditRating);

        CustomerCreditRating customerCreditRating = new CustomerCreditRating();
        customerCreditRating.setCreditUsageIntensityScore(0);
        customerCreditRating.setCreditProductPaymentHabitScore(0);
        customerCreditRating.setCurrentAccountAndDebitStatusScore(0);
        customerCreditRating.setNewCreditProductLaunchesScore(0);
        when(customerCreditRatingService.getCustomerCreditRatingByCustomer(credit.getCustomer()))
                .thenReturn(customerCreditRating);

        creditService.calculateCreditScore(request);
    }

    @Test
    void ShouldCalculateCreditLimitWhenCreditScoreBetween500And1000AndMonthlySalarySmallerThan5000(){
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(4000));

        Credit credit = new Credit();
        credit.setCustomer(customer);

        double creditScore = 750;
        double creditLimitMultiplier = 4;

        BigDecimal creditLimit = creditService.calculateCreditLimit(credit, creditScore, creditLimitMultiplier);

        assertEquals(BigDecimal.valueOf(10000.0), creditLimit);
    }

    @Test
    void ShouldCalculateCreditLimitWhenCreditScoreBetween500And1000AndMonthlySalaryBetween5000And10000(){
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(7500));

        Credit credit = new Credit();
        credit.setCustomer(customer);

        double creditScore = 750;
        double creditLimitMultiplier = 4;

        BigDecimal creditLimit = creditService.calculateCreditLimit(credit, creditScore, creditLimitMultiplier);

        assertEquals(BigDecimal.valueOf(20000.0), creditLimit);
    }

    @Test
    void ShouldCalculateCreditLimitWhenCreditScoreBetween500And1000AndMonthlySalaryAbove10000(){
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(12500));

        Credit credit = new Credit();
        credit.setCustomer(customer);

        double creditScore = 750;
        double creditLimitMultiplier = 4;

        BigDecimal creditLimit = creditService.calculateCreditLimit(credit, creditScore, creditLimitMultiplier);

        assertEquals(BigDecimal.valueOf(25000.0), creditLimit);
    }

    @Test
    void ShouldGetAllCredit(){
        List<Credit> creditList = new ArrayList<>();

        Customer customer = new Customer();
        customer.setName("Ataugur");
        customer.setSurname("Karatas");
        customer.setMonthlySalary(BigDecimal.valueOf(5000));

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setApproval(false);
        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();

        Credit credit1 = new Credit();
        credit1.setResponseTime(null);
        credit1.setGuaranteeCheck(true);
        credit1.setGuaranteeCustomerCheck(true);
        credit1.setCustomer(customer);
        credit1.setGuaranteeCustomer(guaranteeCustomer);

        Credit credit2 = new Credit();
        credit2.setResponseTime(null);
        credit2.setGuaranteeCheck(true);
        credit2.setGuaranteeAssetCheck(true);
        credit2.setCustomer(customer);
        credit2.setGuaranteeAsset(guaranteeAsset);

        creditList.add(credit1);
        creditList.add(credit2);

        when(creditRepository.findAll()).thenReturn(creditList);

        creditService.getAllCredits();
    }

    @Test
    void ShouldThrowNotFoundExceptionWhenGetCredit(){
        String creditId = "id";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                creditService.getCredit(creditId));

        assertEquals("Credit Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCreditWhenGuaranteeAssetCheck(){
        String creditId = "id";

        Customer customer = new Customer();
        customer.setName("Ataugur");
        customer.setSurname("Karatas");
        customer.setMonthlySalary(BigDecimal.valueOf(5000));

        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setGuaranteeAmount(BigDecimal.valueOf(5000));

        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);
        credit.setGuaranteeAssetCheck(true);
        credit.setCustomer(customer);
        credit.setGuaranteeAsset(guaranteeAsset);

        when(creditRepository.findById(creditId)).thenReturn(Optional.of(credit));

        creditService.getCredit(creditId);
    }

    @Test
    void ShouldGetCreditWhenGuaranteeCustomerCheck(){
        String creditId = "id";

        Customer customer = new Customer();
        customer.setName("Ataugur");
        customer.setSurname("Karatas");
        customer.setMonthlySalary(BigDecimal.valueOf(5000));

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setGuaranteeAmount(BigDecimal.valueOf(5000));

        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);
        credit.setGuaranteeCustomerCheck(true);
        credit.setCustomer(customer);
        credit.setGuaranteeCustomer(guaranteeCustomer);

        when(creditRepository.findById(creditId)).thenReturn(Optional.of(credit));

        creditService.getCredit(creditId);
    }

    @Test
    void ShouldGetAllCreditByCustomer(){
        Customer customer = new Customer();
        List<Credit> creditList = new ArrayList<>();
        when(creditRepository.findCreditByCustomer(customer)).thenReturn(creditList);

        creditService.getAllCreditByCustomer(customer);

        verify(creditRepository, times(1)).findCreditByCustomer(customer);
    }

    @Test
    void ShouldCreditScoreBetween500And1000AndMonthlySalaryBelow5000WhenGuaranteeAssetCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);

        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeAsset(guaranteeAsset);
        credit.setGuaranteeAssetCheck(true);

        creditService.creditScoreBetween500And1000AndMonthlySalaryBelow5000(credit);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreBetween500And1000AndMonthlySalaryBelow5000WhenGuaranteeCustomerCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeCustomer(guaranteeCustomer);
        credit.setGuaranteeCustomerCheck(true);

        creditService.creditScoreBetween500And1000AndMonthlySalaryBelow5000(credit);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreBetween500And1000AndMonthlySalaryBetween5000And10000WhenGuaranteeAssetCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);

        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeAsset(guaranteeAsset);
        credit.setGuaranteeAssetCheck(true);

        creditService.creditScoreBetween500And1000AndMonthlySalaryBetween5000And10000(credit);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreBetween500And1000AndMonthlySalaryBetween5000And10000WhenGuaranteeCustomerCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeCustomer(guaranteeCustomer);
        credit.setGuaranteeCustomerCheck(true);

        creditService.creditScoreBetween500And1000AndMonthlySalaryBetween5000And10000(credit);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreBetween500And1000AndMonthlySalaryAbove10000WhenGuaranteeAssetCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(5000));
        credit.setCustomer(customer);

        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeAsset(guaranteeAsset);
        credit.setGuaranteeAssetCheck(true);

        double creditLimitMultiplier = 4;

        creditService.creditScoreBetween500And1000AndMonthlySalaryAbove10000(credit, creditLimitMultiplier);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreBetween500And1000AndMonthlySalaryAbove10000WhenGuaranteeCustomerCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(5000));
        credit.setCustomer(customer);

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeCustomer(guaranteeCustomer);
        credit.setGuaranteeCustomerCheck(true);

        double creditLimitMultiplier = 4;

        creditService.creditScoreBetween500And1000AndMonthlySalaryAbove10000(credit, creditLimitMultiplier);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreAbove1000WhenGuaranteeAssetCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(5000));
        credit.setCustomer(customer);

        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeAsset(guaranteeAsset);
        credit.setGuaranteeAssetCheck(true);

        double creditLimitMultiplier = 4;

        creditService.creditScoreAbove1000(credit, creditLimitMultiplier);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldCreditScoreAbove1000WhenGuaranteeCustomerCheck(){
        Credit credit = new Credit();
        credit.setGuaranteeCheck(true);
        Customer customer = new Customer();
        customer.setMonthlySalary(BigDecimal.valueOf(5000));
        credit.setCustomer(customer);

        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setGuaranteeAmount(BigDecimal.valueOf(5000));
        credit.setGuaranteeCustomer(guaranteeCustomer);
        credit.setGuaranteeCustomerCheck(true);

        double creditLimitMultiplier = 4;

        creditService.creditScoreAbove1000(credit, creditLimitMultiplier);

        verify(creditRepository).save(credit);
    }

    @Test
    void ShouldThrowExceptionWhenGetCreditResult(){
        CreditResultRequest request = new CreditResultRequest("11111111111",
        LocalDate.of(2000,1,1));
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.of(1998,6,4));

        when(customerService.getCustomerByIdentityNumber(request.identityNumber())).thenReturn(customer);

        MismatchException exception = assertThrows(MismatchException.class, () ->
                creditService.getCreditResult(request));

        assertEquals("Identity Number And Date Of Birth Do Not Match", exception.getMessage());
    }

    @Test
    void ShouldGetCreditResult(){
        CreditResultRequest request = new CreditResultRequest("11111111111",
                LocalDate.of(1998,6,4));
        Customer customer = new Customer();
        customer.setId("customerId");
        customer.setName("Ataugur");
        customer.setSurname("Karatas");
        customer.setBirthDate(LocalDate.of(1998,6,4));

        when(customerService.getCustomerByIdentityNumber(request.identityNumber())).thenReturn(customer);

        Credit credit = new Credit();
        credit.setResponseTime(LocalDate.now());
        credit.setPrice(BigDecimal.valueOf(5000));
        credit.setCheck(true);

        when(creditRepository.findLastCreditRecordByCustomer(customer.getId())).thenReturn(credit);

        creditService.getCreditResult(request);
    }
}