package com.definex.credit.service;

import com.definex.credit.dto.CreditRatingDto;
import com.definex.credit.dto.request.AdminInfoUpdateRequest;
import com.definex.credit.dto.request.AuthAdminRegister;
import com.definex.credit.dto.request.AuthEmployeeRegister;
import com.definex.credit.dto.response.AdminResponse;

public interface AdminService {
    void saveEmployee(AuthEmployeeRegister AuthEmployeeRegister);

    void saveAdmin(AuthAdminRegister authEmployeeRegister);

    void saveCurrentCreditRating(CreditRatingDto creditRatingDto);

    void updateAdminInfo(AdminInfoUpdateRequest adminUpdateRequest);

    AdminResponse getAdminByAuthId(String authId);
}
