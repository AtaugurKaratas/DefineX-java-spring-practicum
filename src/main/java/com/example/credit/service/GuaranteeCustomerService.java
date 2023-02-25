package com.example.credit.service;

import com.example.credit.dto.request.UpdateGuaranteeRequest;
import com.example.credit.dto.response.GuaranteeResponse;
import com.example.credit.model.Credit;
import com.example.credit.model.GuaranteeCustomer;

import java.math.BigDecimal;
import java.util.List;

public interface GuaranteeCustomerService {

    GuaranteeResponse getGuarantee(String guaranteeId);

    GuaranteeCustomer saveGuaranteeCustomer(String guaranteeIdentityNumber, BigDecimal guaranteePrice, Credit credit);

    void updateGuarantee(UpdateGuaranteeRequest updateOption);

    List<GuaranteeResponse> getGuaranteeList(String customerId);

    List<GuaranteeResponse> getGuaranteeResults(String customerId);
}
