package com.example.credit.service.impl;

import com.example.credit.dto.request.CustomerRequest;
import com.example.credit.dto.request.CustomerUpdateRequest;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Auth;
import com.example.credit.model.Customer;
import com.example.credit.model.enumeration.ImageType;
import com.example.credit.repository.CustomerRepository;
import com.example.credit.util.ImageProcessing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AuthServiceImpl authService;

    @Mock
    private ImageProcessing imageProcessing;

    @Test
    void ShouldGetAuth() {
        String id = "id";
        Auth auth = new Auth();
        when(authService.getAuth(id)).thenReturn(auth);
        customerService.getAuth(id);
        verify(authService).getAuth(id);
    }

    @Test
    void ShouldThrowExceptionWhenGetCustomer() {
        String customerId = "id";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerService.getCustomer(customerId));

        assertEquals("Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerWhenInputsAreCorrect() {
        String customerId = "id";
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        customerService.getCustomer(customerId);

        verify(customerRepository).findById(customerId);
    }

    @Test
    void ShouldThrowExceptionWhenGetCustomerByAuth() {
        Auth auth = new Auth();

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerService.getCustomerByAuth(auth));

        assertEquals("Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerByAuth() {
        Auth auth = new Auth();
        Customer customer = new Customer();

        when(customerRepository.getCustomerByAuth(auth)).thenReturn(Optional.of(customer));

        customerService.getCustomerByAuth(auth);

        verify(customerRepository).getCustomerByAuth(auth);
    }

    @Test
    void ShouldThrowExceptionWithGetCustomerByIdentityNumber() {
        String identityNumber = "11111111111";
        Auth auth = new Auth();
        when(authService.findByIdentityNumber(identityNumber)).thenReturn(auth);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerService.getCustomerByIdentityNumber(identityNumber));

        assertEquals("Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerByIdentityNumber() {
        String identityNumber = "11111111111";
        Auth auth = new Auth();
        when(authService.findByIdentityNumber(identityNumber)).thenReturn(auth);

        Customer customer = new Customer();
        when(customerRepository.getCustomerByAuth(auth)).thenReturn(Optional.of(customer));

        customerService.getCustomerByIdentityNumber(identityNumber);

        verify(customerRepository).getCustomerByAuth(auth);
    }

    @Test
    void ShouldSaveCustomerWhenImageIsNotNull() {
        CustomerRequest customerRequest = new CustomerRequest("Ataugur"
                , "Karatas"
                , "905405404040"
                , BigDecimal.valueOf(5000)
                , "BASE64"
                , LocalDate.of(1998, 6, 4)
                , "authId");

        when(imageProcessing.imageProcess(customerRequest.imagePath(), ImageType.PROFILE))
                .thenReturn("newProfile.jpeg");

        customerService.saveCustomer(customerRequest);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void ShouldSaveCustomerWhenImageIsNull() {
        CustomerRequest customerRequest = new CustomerRequest("Ataugur"
                , "Karatas"
                , "905405404040"
                , BigDecimal.valueOf(5000)
                , null
                , LocalDate.of(1998, 6, 4)
                , "authId");

        customerService.saveCustomer(customerRequest);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void ShouldThrowExceptionWhenGetCustomerByAuthId() {
        String id = "id";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerService.getCustomerByAuthId(id));

        assertEquals("Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerByAuthIdWhenInputsAreCorrect() {
        String id = "id";
        Customer customer = new Customer();
        customer.setImagePath("profile.jpeg");

        when(customerRepository.getCustomerByAuthId(id)).thenReturn(Optional.of(customer));

        customerService.getCustomerByAuthId(id);
    }

    @Test
    void ShouldThrowsExceptionWhenUpdateCustomer() {
        CustomerUpdateRequest request = new CustomerUpdateRequest("customerId"
                , "Ataugur"
                , "Karatas"
                , "905405404040"
                , BigDecimal.valueOf(5000)
                , LocalDate.of(1998, 6, 4)
                , "imagePath");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerService.updateCustomer(request));

        assertEquals("Customer Not Found", exception.getMessage());
    }

    @Test
    void ShouldUpdateCustomer(){
        CustomerUpdateRequest request = new CustomerUpdateRequest("customerId"
                , "Ataugur"
                , "Karatas"
                , "905405404040"
                , BigDecimal.valueOf(5000)
                , LocalDate.of(1998, 6, 4)
                , "imagePath");

        Customer customer = new Customer();

        when(customerRepository.findById(request.customerId())).thenReturn(Optional.of(customer));

        customerService.updateCustomer(request);

        verify(customerRepository).save(customer);
    }
}