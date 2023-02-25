package com.example.credit.dto.request;

import java.time.LocalDate;

public record EmployeeUpdateRequest(
     String employeeId,
     String employeeName,
     String employeeSurname,
     String phoneNumber,
     LocalDate birthDate,
     String imagePath){
}