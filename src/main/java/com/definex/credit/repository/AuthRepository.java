package com.definex.credit.repository;

import com.definex.credit.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {

    Optional<Auth> findByIdentityNumber(String identityNumber);

    Optional<Auth> findByEmail(String email);
}
