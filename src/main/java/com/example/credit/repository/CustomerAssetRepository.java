package com.example.credit.repository;

import com.example.credit.model.CustomerAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAssetRepository extends JpaRepository<CustomerAsset, String> {

    List<CustomerAsset> findByCustomerId(String customerId);
}
