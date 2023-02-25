package com.example.credit.service;

import com.example.credit.dto.request.*;
import com.example.credit.dto.response.AuthTokenResponse;
import com.example.credit.model.Auth;

public interface AuthService {
    AuthTokenResponse authenticateAndGetToken(AuthLoginRequest authRequest);

    String addUserCustomer(AuthRegisterRequest authRegisterRequest);

    String getUserId(String identityNumber);

    Auth getAuth(String authId);

    Auth addUserEmployee(AuthEmployeeRegister authEmployeeRegister);

    Auth addUserAdmin(AuthAdminRegister authAdminRegister);

    boolean changePassword(ChangePasswordRequest changePassword);

    void accountActivation(String authId, String verifyCode);

    void forgottenPassword(ForgottenPasswordRequest forgottenPasswordRequest);

    void changeForgottenPassword(String authId, String verifyCode, ChangeForgottenPasswordRequest changeForgottenPasswordRequest);

    Auth findByIdentityNumber(String identityNumber);

    void deleteUser(String authId);
}
