package com.example.credit.service.impl;

import com.example.credit.dto.request.AuthEmployeeRegister;
import com.example.credit.dto.request.EmployeeUpdateRequest;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Auth;
import com.example.credit.model.Employee;
import com.example.credit.repository.EmployeeRepository;
import com.example.credit.util.ImageProcessing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuthServiceImpl authService;

    @Mock
    private ImageProcessing imageConvert;

    @Test
    void ShouldSaveEmployeeWhenInputsAreCorrect() {
        AuthEmployeeRegister authEmployeeRegister =
                new AuthEmployeeRegister("11111111111"
                        , "Ataugur"
                        , "Karatas"
                        , "ataugurkaratas@gmail.com"
                        , LocalDate.of(1998, 6, 4)
                        , "905405404040");

        Auth auth = new Auth();
        when(authService.addUserEmployee(authEmployeeRegister)).thenReturn(auth);

        employeeService.saveEmployee(authEmployeeRegister);
        Employee employee = new Employee();
        employee.setName("Ataugur");
        employee.setSurname("Karatas");
        employee.setPhoneNumber("905405404040");
        employee.setBirthDate(LocalDate.of(1998, 6, 4));
        employee.setImagePath("profile.jpeg");
        employee.setAuth(auth);

        verify(employeeRepository).save(employee);
    }

    @Test
    void ShouldThrowExceptionWhenEmployeeNotFound() {
        EmployeeUpdateRequest employeeUpdateRequest =
                new EmployeeUpdateRequest("id"
                        , "Ataugur"
                        , "Karatas"
                        , "905405404040"
                        , LocalDate.of(1998, 6, 4)
                        , "profile.jpeg");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                employeeService.updateEmployee(employeeUpdateRequest));

        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    void ShouldUpdateEmployeeWhenInputsAreCorrect() {
        EmployeeUpdateRequest employeeUpdateRequest =
                new EmployeeUpdateRequest("id"
                        , "Ataugur"
                        , "Karatas"
                        , "905405404040"
                        , LocalDate.of(1998, 6, 4)
                        , "profile.jpeg");

        Employee employee = new Employee();

        when(employeeRepository.findById(employeeUpdateRequest.employeeId()))
                .thenReturn(Optional.of(employee));

        employeeService.updateEmployee(employeeUpdateRequest);

        verify(employeeRepository).save(employee);
    }

    @Test
    void ShouldThrowExceptionWhenGetEmployeeByAuthIdEmployeeNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                employeeService.getEmployeeByAuthId("id"));

        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetEmployeeByAuthIdWhenInputsAreCorrect() {
        Employee employee = new Employee();
        when(employeeRepository.getEmployeeByAuthId("id")).thenReturn(Optional.of(employee));
        employeeService.getEmployeeByAuthId("id");
    }

    @Test
    void ShouldThrowExceptionWhenUpdateEmployeeInfoEmployeeNotFound() {
        EmployeeUpdateRequest employeeUpdateRequest =
                new EmployeeUpdateRequest("id"
                        , "Ataugur"
                        , "Karatas"
                        , "905405404040"
                        , LocalDate.of(1998, 6, 4)
                        , "profile.jpeg");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                employeeService.updateEmployee(employeeUpdateRequest));

        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    void ShouldUpdateEmployeeInfoWhenInputsAreCorrect() {
        EmployeeUpdateRequest employeeUpdateRequest =
                new EmployeeUpdateRequest("id"
                        , "Ataugur"
                        , "Karatas"
                        , "905405404040"
                        , LocalDate.of(1998, 6, 4)
                        , "profile.jpeg");

        Employee employee = new Employee();

        when(employeeRepository.findById("id")).thenReturn(Optional.of(employee));

        employeeService.updateEmployee(employeeUpdateRequest);

        verify(employeeRepository).save(employee);
    }
}