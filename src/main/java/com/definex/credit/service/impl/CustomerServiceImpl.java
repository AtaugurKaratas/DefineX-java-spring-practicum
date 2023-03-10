package com.definex.credit.service.impl;

import com.definex.credit.dto.request.CustomerRequest;
import com.definex.credit.dto.request.CustomerUpdateRequest;
import com.definex.credit.dto.response.CustomerResponse;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Auth;
import com.definex.credit.model.Customer;
import com.definex.credit.model.enumeration.ImageType;
import com.definex.credit.repository.CustomerRepository;
import com.definex.credit.service.CustomerService;
import com.definex.credit.util.ImageProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final AuthServiceImpl authService;

    private final ImageProcessing stringToImage;

    public CustomerServiceImpl(CustomerRepository customerRepository, AuthServiceImpl authService, ImageProcessing stringToImage) {
        this.customerRepository = customerRepository;
        this.authService = authService;
        this.stringToImage = stringToImage;
    }

    @Override
    public Auth getAuth(String id) {
        return authService.getAuth(id);
    }

    @Override
    public Customer saveCustomer(CustomerRequest customerRequest) {
        String image;
        if (customerRequest.imagePath() != null) {
            image = stringToImage.imageProcess(customerRequest.imagePath(), ImageType.PROFILE);
        } else {
            image = "profile.jpeg";
        }
        Customer customer = Customer.builder()
                .name(customerRequest.customerName())
                .surname(customerRequest.customerSurname())
                .phoneNumber(customerRequest.phoneNumber())
                .imagePath(image)
                .monthlySalary(customerRequest.monthlySalary())
                .birthDate(customerRequest.birthDate())
                .auth(this.getAuth(customerRequest.authId())).build();
        customerRepository.save(customer);
        log.info("Auth Id: {} - Added Customer", customerRequest.authId());
        return customer;
    }

    @Override
    public Customer getCustomer(String customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("Customer Id: {} - Customer Not Found", customerId);
            return new NotFoundException("Customer Not Found");
        });
    }

    @Override
    public CustomerResponse getCustomerByAuthId(String id) {
        Customer customer = customerRepository.getCustomerByAuthId(id).orElseThrow(() -> {
            log.warn("Auth Id: {} - Customer Not Found", id);
            return new NotFoundException("Customer Not Found");
        });
        customer.setImagePath("/images/" + customer.getImagePath());
        return new CustomerResponse(customer);
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        String image;
        Customer customer = customerRepository.findById(customerUpdateRequest.customerId()).orElseThrow(() -> {
            log.warn("Customer Id: {} - Customer Not Found", customerUpdateRequest.customerId());
            return new NotFoundException("Customer Not Found");
        });
        if (customerUpdateRequest.imagePath() != null) {
            image = stringToImage.imageProcess(customerUpdateRequest.imagePath(), ImageType.PROFILE);
        } else {
            image = customer.getImagePath();
        }
        customer.setName(customerUpdateRequest.customerName());
        customer.setSurname(customerUpdateRequest.customerSurname());
        customer.setPhoneNumber(customerUpdateRequest.phoneNumber());
        customer.setMonthlySalary(customerUpdateRequest.monthlySalary());
        customer.setBirthDate(customerUpdateRequest.birthDate());
        customer.setImagePath(image);
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByAuth(Auth auth) {
        return customerRepository.getCustomerByAuth(auth).orElseThrow(() -> {
            log.warn("Auth Id: {} - Customer Not Found", auth.getId());
            return new NotFoundException("Customer Not Found");
        });
    }

    @Override
    public Customer getCustomerByIdentityNumber(String identityNumber) {
        Auth auth = authService.findByIdentityNumber(identityNumber);
        return customerRepository.getCustomerByAuth(auth).orElseThrow(() -> {
            log.warn("Auth Id: {} - Customer Not Found", auth.getId());
            return new NotFoundException("Customer Not Found");
        });
    }
}
