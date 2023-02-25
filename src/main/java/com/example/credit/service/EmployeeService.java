package com.example.credit.service;

import com.example.credit.dto.request.AuthEmployeeRegister;
import com.example.credit.dto.request.EmployeeUpdateRequest;
import com.example.credit.dto.response.EmployeeResponse;

public interface EmployeeService {
    void saveEmployee(AuthEmployeeRegister authEmployeeRegister);

    void updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);

    EmployeeResponse getEmployeeByAuthId(String authId);
}
