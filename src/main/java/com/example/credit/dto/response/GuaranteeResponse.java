package com.example.credit.dto.response;

import com.example.credit.model.Customer;
import com.example.credit.model.GuaranteeAsset;
import com.example.credit.model.GuaranteeCustomer;

import java.math.BigDecimal;

public record GuaranteeResponse(
        String id,
        boolean guaranteeCheck,
        BigDecimal guaranteeAmount,
        String customerName,
        String customerSurname,
        String imagePath) {
    public GuaranteeResponse(GuaranteeCustomer guaranteeCustomer, Customer customer) {
        this(guaranteeCustomer.getId()
                , guaranteeCustomer.isGuaranteeCheck()
                , guaranteeCustomer.getGuaranteeAmount()
                , customer.getName()
                , customer.getSurname()
                , ("/images/" + customer.getImagePath()));
    }

    public GuaranteeResponse(GuaranteeAsset guaranteeAsset, Customer customer) {
        this(guaranteeAsset.getId()
                , true
                , guaranteeAsset.getGuaranteeAmount()
                , customer.getName()
                , customer.getSurname()
                , ("/images/" + customer.getImagePath()));
    }
}