package com.definex.credit.dto.response;

import com.definex.credit.model.Employee;

import java.time.LocalDate;

public record EmployeeResponse(
        String employeeId,
        String name,
        String surname,
        String phoneNumber,
        LocalDate birthDate,
        String imagePath) {
    public EmployeeResponse(Employee employee) {
        this(employee.getId()
                , employee.getName()
                , employee.getSurname()
                , employee.getPhoneNumber()
                , employee.getBirthDate()
                , employee.getImagePath());
    }
}