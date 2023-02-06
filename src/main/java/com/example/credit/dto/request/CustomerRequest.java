package com.example.credit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private String name;

    private String surname;

    private String phoneNumber;

    private BigDecimal monthlySalary;

    private String imagePath;

    private String authId;
}
