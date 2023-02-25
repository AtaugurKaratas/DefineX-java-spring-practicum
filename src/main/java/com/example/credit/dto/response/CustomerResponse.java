package com.example.credit.dto.response;

import com.example.credit.model.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustomerResponse(
        String customerId,
        String name,
        String surname,
        String phoneNumber,
        BigDecimal monthlySalary,
        LocalDate birthDate,
        String imagePath) {
    public CustomerResponse(Customer customer) {
        this(customer.getId()
                , customer.getName()
                , customer.getSurname()
                , customer.getPhoneNumber()
                , customer.getMonthlySalary()
                , customer.getBirthDate()
                , customer.getImagePath());
    }
}