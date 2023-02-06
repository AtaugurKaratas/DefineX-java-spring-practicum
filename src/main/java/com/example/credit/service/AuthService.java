package com.example.credit.service;

import com.example.credit.dto.request.AuthRegisterRequest;

public interface AuthService {

    public String addUserCustomer(AuthRegisterRequest authRegisterRequest);

    public String getUserId(String identityNumber);

}
