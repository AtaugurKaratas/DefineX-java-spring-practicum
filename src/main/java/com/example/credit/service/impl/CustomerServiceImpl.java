package com.example.credit.service.impl;

import com.example.credit.dto.request.CustomerRequest;
import com.example.credit.model.Auth;
import com.example.credit.model.Customer;
import com.example.credit.repository.CustomerRepository;
import com.example.credit.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final AuthServiceImpl authService;

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(CustomerRepository customerRepository, AuthServiceImpl authService) {
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

    public Auth getAuth(String auth_id){
        return authService.getAuth(auth_id);
    }

    @Override
    public void saveCustomer(CustomerRequest customerRequest) {
        logger.info(customerRequest.getAuthId());
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .phoneNumber(customerRequest.getPhoneNumber())
                .imagePath(customerRequest.getImagePath())
                .monthlySalary(customerRequest.getMonthlySalary())
                .auth(getAuth(customerRequest.getAuthId())).build();
        customerRepository.save(customer);
    }
}
