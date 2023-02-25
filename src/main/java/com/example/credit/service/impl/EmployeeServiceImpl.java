package com.example.credit.service.impl;

import com.example.credit.dto.request.AuthEmployeeRegister;
import com.example.credit.dto.request.EmployeeUpdateRequest;
import com.example.credit.dto.response.EmployeeResponse;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Auth;
import com.example.credit.model.Employee;
import com.example.credit.model.enumeration.ImageType;
import com.example.credit.repository.EmployeeRepository;
import com.example.credit.service.EmployeeService;
import com.example.credit.util.ImageProcessing;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final AuthServiceImpl authService;

    private final ImageProcessing imageConvert;

    @Transactional
    @Override
    public void saveEmployee(AuthEmployeeRegister authEmployeeRegister) {
        Auth auth = authService.addUserEmployee(authEmployeeRegister);
        Employee employee = new Employee();
        employee.setName(authEmployeeRegister.name());
        employee.setSurname(authEmployeeRegister.surname());
        employee.setPhoneNumber(authEmployeeRegister.phoneNumber());
        employee.setBirthDate(authEmployeeRegister.birthDate());
        employee.setImagePath("profile.jpeg");
        employee.setAuth(auth);
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        Employee employee = employeeRepository.findById(employeeUpdateRequest.employeeId()).orElseThrow(() -> {
            log.warn("updateEmployee - Employee Not Found");
            return new NotFoundException("Employee Not Found");
        });
        employee.setName(employeeUpdateRequest.employeeName());
        employee.setSurname(employeeUpdateRequest.employeeSurname());
        employee.setPhoneNumber(employeeUpdateRequest.phoneNumber());
        employee.setBirthDate(employeeUpdateRequest.birthDate());
        if(employeeUpdateRequest.imagePath() != null){
            String image = imageConvert.imageProcess(employeeUpdateRequest.imagePath(), ImageType.PROFILE);
            employee.setImagePath(image);
        }
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse getEmployeeByAuthId(String authId) {
        Employee employee = employeeRepository.getEmployeeByAuthId(authId).orElseThrow(() -> {
            log.warn("getEmployeeByAuthId - Employee Not Found");
            return new NotFoundException("Employee Not Found");
        });
        employee.setImagePath("/images/" + employee.getImagePath());
        return new EmployeeResponse(employee);
    }
}
