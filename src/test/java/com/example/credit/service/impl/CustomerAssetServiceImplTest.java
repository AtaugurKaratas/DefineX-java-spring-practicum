package com.example.credit.service.impl;

import com.example.credit.dto.request.CustomerAssetRequest;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Customer;
import com.example.credit.model.CustomerAsset;
import com.example.credit.repository.CustomerAssetRepository;
import com.example.credit.util.ImageProcessing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerAssetServiceImplTest {

    @InjectMocks
    private CustomerAssetServiceImpl customerAssetService;

    @Mock
    private CustomerAssetRepository customerAssetRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private ImageProcessing stringToImage;

    @Test
    void ShouldSaveCustomerAssetWhenImagePathIsNotNull() {
        CustomerAssetRequest customerAssetRequest = new CustomerAssetRequest("House"
                , BigDecimal.valueOf(100000)
                , "house.png"
                , "customerId");

        Customer customer = new Customer();
        when(customerService.getCustomer(customerAssetRequest.customerId())).thenReturn(customer);

        customerAssetService.saveCustomerAsset(customerAssetRequest);

        verify(customerAssetRepository).save(any(CustomerAsset.class));
    }

    @Test
    void ShouldSaveCustomerAssetWhenImagePathInputIsTrue() {
        CustomerAssetRequest customerAssetRequest = new CustomerAssetRequest("House"
                , BigDecimal.valueOf(100000)
                , "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej" +
                "3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII="
                , "customerId");

        Customer customer = new Customer();
        when(customerService.getCustomer(customerAssetRequest.customerId())).thenReturn(customer);

        customerAssetService.saveCustomerAsset(customerAssetRequest);

        verify(customerAssetRepository).save(any(CustomerAsset.class));
    }

    @Test
    void ShouldSaveCustomerAssetWhenImagePathIsNull() {
        CustomerAssetRequest customerAssetRequest = new CustomerAssetRequest("House"
                , BigDecimal.valueOf(100000)
                , null
                , "customerId");

        Customer customer = new Customer();
        when(customerService.getCustomer(customerAssetRequest.customerId())).thenReturn(customer);

        customerAssetService.saveCustomerAsset(customerAssetRequest);

        verify(customerAssetRepository).save(any(CustomerAsset.class));
    }

    @Test
    void ShouldGetCustomerAssets() {
        String customerId = "id";
        CustomerAsset customerAsset1 = new CustomerAsset();
        CustomerAsset customerAsset2 = new CustomerAsset();
        List<CustomerAsset> customerAssetList = new ArrayList<>();
        customerAssetList.add(customerAsset1);
        customerAssetList.add(customerAsset2);
        when(customerAssetRepository.findByCustomerId(customerId)).thenReturn(customerAssetList);
        customerAssetService.getCustomerAssets(customerId);
    }

    @Test
    void ShouldThrowExceptionWhenGetCustomerAsset() {
        String customerAssetId = "id";
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerAssetService.getCustomerAsset(customerAssetId));

        assertEquals("Customer Asset Not Found", exception.getMessage());
    }

    @Test
    void ShouldGetCustomerAsset() {
        String customerAssetId = "id";
        CustomerAsset customerAsset = mock(CustomerAsset.class);
        when(customerAssetRepository.findById(customerAssetId))
                .thenReturn(Optional.of(customerAsset));
        customerAssetService.getCustomerAsset(customerAssetId);
    }
}