package com.definex.credit.service.impl;

import com.definex.credit.dto.request.UpdateGuaranteeRequest;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Credit;
import com.definex.credit.model.Customer;
import com.definex.credit.model.GuaranteeCustomer;
import com.definex.credit.repository.GuaranteeCustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GuaranteeCustomerServiceImplTest {

    @InjectMocks
    private GuaranteeCustomerServiceImpl guaranteeCustomerService;

    @Mock
    private GuaranteeCustomerRepository guaranteeCustomerRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @Test
    void ShouldSaveGuaranteeCustomer() {
        String guaranteeIdentityNumber = "12345678901";
        BigDecimal guaranteePrice = BigDecimal.valueOf(5000);
        Credit credit = new Credit();
        Customer customer = new Customer();
        when(customerService.getCustomerByIdentityNumber(guaranteeIdentityNumber)).thenReturn(customer);
        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setCredit(credit);
        guaranteeCustomer.setCustomer(customer);
        guaranteeCustomer.setGuaranteeAmount(guaranteePrice);

        guaranteeCustomerService.saveGuaranteeCustomer(guaranteeIdentityNumber
                , guaranteePrice
                , credit);

        verify(guaranteeCustomerRepository).save(refEq(guaranteeCustomer));
    }

    @Test
    void ShouldUpdateGuarantee() {
        UpdateGuaranteeRequest updateGuaranteeRequest =
                new UpdateGuaranteeRequest("id", true);
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guaranteeCustomerService.updateGuarantee(updateGuaranteeRequest));

        assertEquals("Guarantee Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldUpdateGuaranteeInputsAreCorrect() {
        UpdateGuaranteeRequest updateGuaranteeRequest =
                new UpdateGuaranteeRequest("id", true);
        Customer customer = mock(Customer.class);
        Credit credit = mock(Credit.class);
        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer(customer
                , BigDecimal.valueOf(5000)
                , false
                , LocalDate.now()
                , true
                , credit);
        when(guaranteeCustomerRepository.findById(updateGuaranteeRequest.guaranteeId()))
                .thenReturn(Optional.of(guaranteeCustomer));

        guaranteeCustomerService.updateGuarantee(updateGuaranteeRequest);

        verify(guaranteeCustomerRepository).save(guaranteeCustomer);
    }

    @Test
    void ShouldGetGuaranteeList(){
        String customerId = "id";
        Customer customer = new Customer();
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        Credit credit1 = new Credit();
        Customer customer1 = new Customer();
        credit1.setCustomer(customer1);
        Credit credit2 = new Credit();
        Customer customer2 = new Customer();
        credit2.setCustomer(customer2);

        GuaranteeCustomer guaranteeCustomer1 = new GuaranteeCustomer();
        guaranteeCustomer1.setGuaranteeCheck(true);
        guaranteeCustomer1.setCredit(credit1);
        GuaranteeCustomer guaranteeCustomer2 = new GuaranteeCustomer();
        guaranteeCustomer2.setGuaranteeCheck(false);
        guaranteeCustomer2.setCredit(credit2);

        List<GuaranteeCustomer> guaranteeCustomerList = new ArrayList<>();
        guaranteeCustomerList.add(guaranteeCustomer1);
        guaranteeCustomerList.add(guaranteeCustomer2);

        when(guaranteeCustomerRepository.findByCustomer(customer)).thenReturn(guaranteeCustomerList);

        guaranteeCustomerService.getGuaranteeList(customerId);
    }

    @Test
    void ShouldThrownExceptionWithGetGuarantee(){
        String customerGuaranteeId = "id";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guaranteeCustomerService.getGuarantee(customerGuaranteeId));

        assertEquals("Guarantee Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetGuarantee(){
        String customerGuaranteeId = "id";
        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        Credit credit = new Credit();
        Customer customer = new Customer();
        credit.setCustomer(customer);
        guaranteeCustomer.setCredit(credit);
        when(guaranteeCustomerRepository.findById(customerGuaranteeId))
                .thenReturn(Optional.of(guaranteeCustomer));
        guaranteeCustomerService.getGuarantee(customerGuaranteeId);

    }

    @Test
    void ShouldGetGuaranteeResults(){
        String customerId = "id";
        Customer customer = new Customer();
        customer.setName("Ataugur");
        customer.setSurname("Karatas");
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        List<GuaranteeCustomer> guaranteeCustomers = new ArrayList<>();

        GuaranteeCustomer guaranteeCustomer1 = new GuaranteeCustomer();
        guaranteeCustomer1.setGuaranteeCheck(true);
        Customer customer1 = new Customer();
        customer1.setId("customer1");
        customer1.setName("Ataugur");
        customer1.setSurname("Karatas");
        guaranteeCustomer1.setCustomer(customer1);

        GuaranteeCustomer guaranteeCustomer2 = new GuaranteeCustomer();
        Customer customer2 = new Customer();
        customer2.setId("customer2");
        customer2.setName("Ataugur");
        customer2.setSurname("Karatas");
        guaranteeCustomer2.setCustomer(customer2);

        guaranteeCustomers.add(guaranteeCustomer1);
        guaranteeCustomers.add(guaranteeCustomer2);

        when(guaranteeCustomerRepository.findByCustomer(customer)).thenReturn(guaranteeCustomers);
        when(customerService.getCustomer(guaranteeCustomer1.getCustomer().getId())).thenReturn(customer1);
        guaranteeCustomerService.getGuaranteeResults(customerId);
    }

}