package com.definex.credit.service.impl;

import com.definex.credit.util.ImageProcessing;
import com.definex.credit.dto.request.AuthEmployeeRegister;
import com.definex.credit.dto.request.EmployeeUpdateRequest;
import com.definex.credit.dto.response.EmployeeResponse;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Auth;
import com.definex.credit.model.Employee;
import com.definex.credit.model.enumeration.ImageType;
import com.definex.credit.repository.EmployeeRepository;
import com.definex.credit.service.EmployeeService;
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
        log.info(employeeUpdateRequest.toString());
        Employee employee = employeeRepository.findById(employeeUpdateRequest.employeeId()).orElseThrow(() -> {
            log.warn("Employee Id: {} - Employee Not Found", employeeUpdateRequest.employeeId());
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
            log.warn("Auth Id: {} - Employee Not Found", authId);
            return new NotFoundException("Employee Not Found");
        });
        employee.setImagePath("/images/" + employee.getImagePath());
        return new EmployeeResponse(employee);
    }
}
