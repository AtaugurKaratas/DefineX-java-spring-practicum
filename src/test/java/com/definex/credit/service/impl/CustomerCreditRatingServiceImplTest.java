package com.definex.credit.service.impl;

import com.definex.credit.dto.request.CustomerCreditRatingRequest;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Customer;
import com.definex.credit.model.CustomerCreditRating;
import com.definex.credit.repository.CustomerCreditRatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerCreditRatingServiceImplTest {

    @InjectMocks
    private CustomerCreditRatingServiceImpl customerCreditRatingService;

    @Mock
    private CustomerCreditRatingRepository customerCreditRatingRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @Test
    void ShouldSaveCustomerCreditRating() {
        Customer customer = new Customer();
        customerCreditRatingService.saveCustomerCreditRating(customer);
        verify(customerCreditRatingRepository).save(any(CustomerCreditRating.class));
    }

    @Test
    void ShouldGetCustomer(){
        String customerId = "id";
        customerCreditRatingService.getCustomer(customerId);
        verify(customerService).getCustomer(customerId);
    }

    @Test
    void ShouldThrowExceptionWhenUpdateCustomerCreditRating(){
        CustomerCreditRatingRequest customerCreditRatingRequest =
                new CustomerCreditRatingRequest("id"
                ,25
                ,25
                ,25
                ,25);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerCreditRatingService.updateCustomerCreditRating(customerCreditRatingRequest));

        assertEquals("Customer Credit Rating Not Found", exception.getMessage());
    }

    @Test
    void ShouldUpdateCustomerCreditRating(){
        CustomerCreditRatingRequest customerCreditRatingRequest =
                new CustomerCreditRatingRequest("id"
                        ,25
                        ,25
                        ,25
                        ,25);
        CustomerCreditRating customerCreditRating = new CustomerCreditRating();
        when(customerCreditRatingRepository.findById(customerCreditRatingRequest.id()))
                .thenReturn(Optional.of(customerCreditRating));
        customerCreditRatingService.updateCustomerCreditRating(customerCreditRatingRequest);
        verify(customerCreditRatingRepository).save(customerCreditRating);
    }

    @Test
    void ShouldThrowExceptionWhenGetCustomerCreditRatingByCustomer(){
        Customer customer = new Customer();

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerCreditRatingService.getCustomerCreditRatingByCustomer(customer));

        assertEquals("Customer Credit Rating Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerCreditRatingByCustomer(){
        Customer customer = new Customer();

        CustomerCreditRating customerCreditRating = new CustomerCreditRating();

        when(customerCreditRatingRepository.getCustomerCreditRatingByCustomer(customer))
                .thenReturn(Optional.of(customerCreditRating));

        customerCreditRatingService.getCustomerCreditRatingByCustomer(customer);

        verify(customerCreditRatingRepository).getCustomerCreditRatingByCustomer(customer);
    }

    @Test
    void ShouldThrowExceptionWhenGetCustomerCreditRating(){
        String identityNumber = "11111111111";
        Customer customer = new Customer();
        when(customerService.getCustomerByIdentityNumber(identityNumber)).thenReturn(customer);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerCreditRatingService.getCustomerCreditRating(identityNumber));

        assertEquals("Customer Credit Rating Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerCreditRating(){
        String identityNumber = "11111111111";
        Customer customer = new Customer();
        customer.setName("Ataugur");
        customer.setSurname("Karatas");
        when(customerService.getCustomerByIdentityNumber(identityNumber)).thenReturn(customer);

        CustomerCreditRating customerCreditRating = new CustomerCreditRating();
        customerCreditRating.setCustomer(customer);

        when(customerCreditRatingRepository.getCustomerCreditRatingByCustomer(customer))
                .thenReturn(Optional.of(customerCreditRating));

        customerCreditRatingService.getCustomerCreditRating(identityNumber);

        verify(customerCreditRatingRepository).getCustomerCreditRatingByCustomer(customer);
    }
}