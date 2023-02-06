package com.example.credit.repository;

import com.example.credit.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {
    Optional<Auth> findByIdentityNumber(String identityNumber);

    Optional<Auth> findById(String auth_id);
}
