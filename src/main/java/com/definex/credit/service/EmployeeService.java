package com.definex.credit.service;

import com.definex.credit.dto.request.AuthEmployeeRegister;
import com.definex.credit.dto.request.EmployeeUpdateRequest;
import com.definex.credit.dto.response.EmployeeResponse;

public interface EmployeeService {
    void saveEmployee(AuthEmployeeRegister authEmployeeRegister);

    void updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);

    EmployeeResponse getEmployeeByAuthId(String authId);
}
