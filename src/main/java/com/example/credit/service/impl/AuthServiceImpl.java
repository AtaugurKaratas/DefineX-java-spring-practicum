package com.example.credit.service.impl;

import com.example.credit.dto.request.AuthRegisterRequest;
import com.example.credit.model.Auth;
import com.example.credit.model.enumeration.RoleType;
import com.example.credit.repository.AuthRepository;
import com.example.credit.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addUserCustomer(AuthRegisterRequest authRegisterRequest) {
        authRegisterRequest.setPassword(passwordEncoder.encode(authRegisterRequest.getPassword()));
        Auth auth = Auth.builder()
                .identityNumber(authRegisterRequest.getIdentityNumber())
                .email(authRegisterRequest.getEmail())
                .password(authRegisterRequest.getPassword())
                .roles(RoleType.ROLE_CUSTOMER.toString()).build();
        authRepository.save(auth);
        auth = authRepository.findByIdentityNumber(authRegisterRequest.getIdentityNumber()).orElseThrow();
        return auth.getId();
    }

    public String getUserId(String identityNumber) {
        Auth auth = authRepository.findByIdentityNumber(identityNumber).orElseThrow();
        return auth.getId();
    }

}
