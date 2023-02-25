package com.example.credit.service;

import com.example.credit.dto.CreditRatingDto;
import com.example.credit.dto.request.AdminInfoUpdateRequest;
import com.example.credit.dto.request.AuthAdminRegister;
import com.example.credit.dto.request.AuthEmployeeRegister;
import com.example.credit.dto.response.AdminResponse;

public interface AdminService {
    void saveEmployee(AuthEmployeeRegister AuthEmployeeRegister);

    void saveAdmin(AuthAdminRegister authEmployeeRegister);

    void saveCurrentCreditRating(CreditRatingDto creditRatingDto);

    void updateAdminInfo(AdminInfoUpdateRequest adminUpdateRequest);

    AdminResponse getAdminByAuthId(String authId);
}
