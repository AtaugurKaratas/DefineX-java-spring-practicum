package com.example.credit.service.impl;

import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Credit;
import com.example.credit.model.Customer;
import com.example.credit.model.CustomerAsset;
import com.example.credit.model.GuaranteeAsset;
import com.example.credit.repository.GuaranteeAssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuaranteeAssetServiceImplTest {

    @InjectMocks
    private GuaranteeAssetServiceImpl guaranteeAssetService;

    @Mock
    private GuaranteeAssetRepository guaranteeAssetRepository;

    @Mock
    private CustomerAssetServiceImpl customerAssetService;

    @Test
    void ShouldSaveGuaranteeAsset(){
        String customerAssetId = "Id";
        BigDecimal guaranteeAmount = BigDecimal.valueOf(5000);
        Credit credit = new Credit();

        CustomerAsset customerAsset = new CustomerAsset();
        when(customerAssetService.getCustomerAsset(customerAssetId)).thenReturn(customerAsset);

        guaranteeAssetService.saveGuaranteeAsset(customerAssetId, guaranteeAmount, credit);

        verify(guaranteeAssetRepository).save(any(GuaranteeAsset.class));
    }

    @Test
    void ShouldThrowExceptionWhenGetGuarantee(){
        String guaranteeId = "id";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guaranteeAssetService.getGuarantee(guaranteeId));

        assertEquals("Guarantee Asset Not Found", exception.getMessage());
    }

    @Test
    void ShouldSaveGuaranteeAssetWhenInputsAreCorrect(){
        String guaranteeId = "id";
        Credit credit = new Credit();
        Customer customer = new Customer();
        credit.setCustomer(customer);
        GuaranteeAsset guaranteeAsset = new GuaranteeAsset();
        guaranteeAsset.setCredit(credit);
        when(guaranteeAssetRepository.findById(guaranteeId)).thenReturn(Optional.of(guaranteeAsset));
        guaranteeAssetService.getGuarantee(guaranteeId);
    }

    @Test
    void ShouldGetGuaranteeAsset(){
        String guaranteeAssetId = "id";
        guaranteeAssetService.getGuaranteeAsset(guaranteeAssetId);
        verify(guaranteeAssetRepository).getReferenceById(guaranteeAssetId);
    }
}