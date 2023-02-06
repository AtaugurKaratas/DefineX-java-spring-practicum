package com.example.credit.service;

import com.example.credit.dto.request.AuthRegisterRequest;
import com.example.credit.model.Auth;

public interface AuthService {

    public String addUserCustomer(AuthRegisterRequest authRegisterRequest);

    public String getUserId(String identityNumber);

    public Auth getAuth(String authId);

}
