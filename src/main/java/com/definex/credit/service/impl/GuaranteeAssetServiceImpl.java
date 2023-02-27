package com.definex.credit.service.impl;

import com.definex.credit.dto.response.GuaranteeResponse;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Credit;
import com.definex.credit.model.Customer;
import com.definex.credit.model.CustomerAsset;
import com.definex.credit.model.GuaranteeAsset;
import com.definex.credit.repository.GuaranteeAssetRepository;
import com.definex.credit.service.GuaranteeAssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuaranteeAssetServiceImpl implements GuaranteeAssetService {

    private final GuaranteeAssetRepository guaranteeAssetRepository;

    private final CustomerAssetServiceImpl customerAssetService;

    @Override
    public GuaranteeAsset saveGuaranteeAsset(String customerAssetId, BigDecimal guaranteeAmount, Credit credit){
        CustomerAsset customerAsset = customerAssetService.getCustomerAsset(customerAssetId);
        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setCustomerAsset(customerAsset);
        guaranteeAsset.setGuaranteeAmount(guaranteeAmount);
        guaranteeAsset.setCredit(credit);
        guaranteeAssetRepository.save(guaranteeAsset);
        return guaranteeAsset;
    }

    @Override
    public GuaranteeResponse getGuarantee(String guaranteeId) {
        GuaranteeAsset guaranteeAsset = guaranteeAssetRepository.findById(guaranteeId).orElseThrow(() -> {
            log.warn("Guarantee Id: {} - Guarantee Asset Not Found", guaranteeId);
            return new NotFoundException("Guarantee Asset Not Found");
        });
        Credit credit = guaranteeAsset.getCredit();
        Customer customer = credit.getCustomer();
        return new GuaranteeResponse(guaranteeAsset, customer);
    }

    @Override
    public GuaranteeAsset getGuaranteeAsset(String guaranteeAssetId){
        return guaranteeAssetRepository.getReferenceById(guaranteeAssetId);
    }
}
