package com.definex.credit.controller;

import com.definex.credit.service.impl.EmployeeServiceImpl;
import com.definex.credit.dto.request.EmployeeUpdateRequest;
import com.definex.credit.dto.response.EmployeeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public ResponseEntity<?> employeeUpdate(@Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        employeeService.updateEmployee(employeeUpdateRequest);
        return new ResponseEntity<>("User Updated", HttpStatus.OK);
    }

    @GetMapping("/{authId}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public EmployeeResponse getEmployee(@PathVariable String authId) {
        return employeeService.getEmployeeByAuthId(authId);
    }

    @PutMapping("/employee-info-update")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public ResponseEntity<String> updateEmployeeInfo(@Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest){
        employeeService.updateEmployee(employeeUpdateRequest);
        return new ResponseEntity<>("Employee Info Updated", HttpStatus.OK);
    }
}
