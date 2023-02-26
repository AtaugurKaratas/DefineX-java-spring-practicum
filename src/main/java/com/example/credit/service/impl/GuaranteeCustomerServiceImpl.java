package com.example.credit.service.impl;

import com.example.credit.dto.request.UpdateGuaranteeRequest;
import com.example.credit.dto.response.GuaranteeResponse;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Credit;
import com.example.credit.model.Customer;
import com.example.credit.model.GuaranteeCustomer;
import com.example.credit.repository.GuaranteeCustomerRepository;
import com.example.credit.service.GuaranteeCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuaranteeCustomerServiceImpl implements GuaranteeCustomerService {

    private final GuaranteeCustomerRepository guaranteeCustomerRepository;

    private final CustomerServiceImpl customerService;

    @Override
    public GuaranteeCustomer saveGuaranteeCustomer(String guaranteeIdentityNumber, BigDecimal guaranteePrice, Credit credit) {
        Customer customer = customerService.getCustomerByIdentityNumber(guaranteeIdentityNumber);
        GuaranteeCustomer guaranteeCustomer = new GuaranteeCustomer();
        guaranteeCustomer.setCustomer(customer);
        guaranteeCustomer.setGuaranteeAmount(guaranteePrice);
        guaranteeCustomer.setCredit(credit);
        guaranteeCustomerRepository.save(guaranteeCustomer);
        return guaranteeCustomer;
    }

    @Override
    public void updateGuarantee(UpdateGuaranteeRequest updateGuaranteeRequest) {
        GuaranteeCustomer guaranteeCustomer =
                guaranteeCustomerRepository.findById(updateGuaranteeRequest.guaranteeId()).orElseThrow(() -> {
                    log.warn("Guarantee Id: {} - Guarantee Customer Not Found", updateGuaranteeRequest.guaranteeId());
                    return new NotFoundException("Guarantee Customer Not Found");
                });
        if (!guaranteeCustomer.isGuaranteeCheck()) {
            guaranteeCustomer.setGuaranteeCheck(true);
            guaranteeCustomer.setApproval(updateGuaranteeRequest.approval());
            guaranteeCustomer.setResponseTime(LocalDate.now());
            guaranteeCustomerRepository.save(guaranteeCustomer);
        }
    }

    @Override
    public List<GuaranteeResponse> getGuaranteeList(String customerId) {
        List<GuaranteeResponse> guaranteeResponses = new ArrayList<>();
        Customer customer = customerService.getCustomer(customerId);
        List<GuaranteeCustomer> guaranteeCustomerList = guaranteeCustomerRepository.findByCustomer(customer);
        for (GuaranteeCustomer guaranteeCustomer : guaranteeCustomerList) {
            if (!guaranteeCustomer.isGuaranteeCheck()) {
                GuaranteeResponse guaranteeResponse =
                        new GuaranteeResponse(guaranteeCustomer, guaranteeCustomer.getCredit().getCustomer());
                guaranteeResponses.add(guaranteeResponse);
            }
        }
        return guaranteeResponses;
    }

    @Override
    public GuaranteeResponse getGuarantee(String customerGuaranteeId) {
        GuaranteeCustomer guaranteeCustomer = guaranteeCustomerRepository.findById(customerGuaranteeId).orElseThrow(() -> {
            log.warn("Customer Guarantee Id: {} - Guarantee Customer Not Found", customerGuaranteeId);
            return new NotFoundException("Guarantee Customer Not Found");
        });
        Credit credit = guaranteeCustomer.getCredit();
        Customer customer = credit.getCustomer();
        return new GuaranteeResponse(guaranteeCustomer, customer);
    }

    @Override
    public List<GuaranteeResponse> getGuaranteeResults(String customerId) {
        List<GuaranteeResponse> guaranteeResponses = new ArrayList<>();
        Customer customer = customerService.getCustomer(customerId);
        List<GuaranteeCustomer> guaranteeCustomers = guaranteeCustomerRepository.findByCustomer(customer);
        for (GuaranteeCustomer guaranteeCustomer : guaranteeCustomers) {
            if (guaranteeCustomer.isGuaranteeCheck()) {
                customer = customerService.getCustomer(guaranteeCustomer.getCustomer().getId());
                GuaranteeResponse optionResponse =
                        new GuaranteeResponse(guaranteeCustomer, customer);
                guaranteeResponses.add(optionResponse);
            }
        }
        return guaranteeResponses;
    }
}
