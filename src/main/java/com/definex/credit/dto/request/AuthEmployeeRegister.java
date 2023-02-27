package com.definex.credit.dto.request;

import com.definex.credit.annotation.UniqueEmail;
import com.definex.credit.annotation.UniqueIdentityNumber;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;


public record AuthEmployeeRegister(
        @UniqueIdentityNumber String identityNumber,
        String name,
        String surname,
        @UniqueEmail String email,
        @Past LocalDate birthDate,
        String phoneNumber) {
}