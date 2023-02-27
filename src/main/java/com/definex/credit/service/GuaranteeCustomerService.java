package com.definex.credit.service;

import com.definex.credit.dto.request.UpdateGuaranteeRequest;
import com.definex.credit.dto.response.GuaranteeResponse;
import com.definex.credit.model.Credit;
import com.definex.credit.model.GuaranteeCustomer;

import java.math.BigDecimal;
import java.util.List;

public interface GuaranteeCustomerService {

    GuaranteeResponse getGuarantee(String guaranteeId);

    GuaranteeCustomer saveGuaranteeCustomer(String guaranteeIdentityNumber, BigDecimal guaranteePrice, Credit credit);

    void updateGuarantee(UpdateGuaranteeRequest updateOption);

    List<GuaranteeResponse> getGuaranteeList(String customerId);

    List<GuaranteeResponse> getGuaranteeResults(String customerId);
}
