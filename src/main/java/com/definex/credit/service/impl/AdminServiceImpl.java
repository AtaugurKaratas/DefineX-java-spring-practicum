package com.definex.credit.service.impl;

import com.definex.credit.dto.CreditRatingDto;
import com.definex.credit.dto.request.AdminInfoUpdateRequest;
import com.definex.credit.dto.request.AuthAdminRegister;
import com.definex.credit.dto.request.AuthEmployeeRegister;
import com.definex.credit.dto.response.AdminResponse;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Admin;
import com.definex.credit.model.Auth;
import com.definex.credit.model.enumeration.ImageType;
import com.definex.credit.repository.AdminRepository;
import com.definex.credit.service.AdminService;
import com.definex.credit.util.ImageProcessing;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final EmployeeServiceImpl employeeService;

    private final AuthServiceImpl authService;

    private final CreditRatingServiceImpl creditRatingService;

    private final ImageProcessing imageConvert;

    @Override
    public void saveEmployee(AuthEmployeeRegister authEmployeeRegister) {
        employeeService.saveEmployee(authEmployeeRegister);
    }

    @Transactional
    @Override
    public void saveAdmin(AuthAdminRegister authAdminRegister) {
        Auth auth = authService.addUserAdmin(authAdminRegister);
        Admin admin = new Admin();
        admin.setName(authAdminRegister.name());
        admin.setSurname(authAdminRegister.surname());
        admin.setPhoneNumber(authAdminRegister.phoneNumber());
        admin.setBirthDate(authAdminRegister.birthDate());
        admin.setImagePath("profile.jpeg");
        admin.setAuth(auth);
        adminRepository.save(admin);
    }

    @Override
    public void saveCurrentCreditRating(CreditRatingDto creditRatingDto) {
        creditRatingService.saveCurrentCreditRating(creditRatingDto);
    }

    @Override
    public void updateAdminInfo(AdminInfoUpdateRequest adminInfoUpdateRequest) {
        Admin admin = adminRepository.findById(adminInfoUpdateRequest.adminId()).orElseThrow(() -> {
            log.warn("Admin Id: {} - Admin Not Found", adminInfoUpdateRequest.adminId());
            return new NotFoundException("Admin Not Found");
        });
        admin.setName(adminInfoUpdateRequest.adminName());
        admin.setSurname(adminInfoUpdateRequest.adminSurname());
        admin.setPhoneNumber(adminInfoUpdateRequest.phoneNumber());
        admin.setBirthDate(adminInfoUpdateRequest.birthDate());
        if (adminInfoUpdateRequest.imagePath() != null) {
            String image = imageConvert.imageProcess(adminInfoUpdateRequest.imagePath(), ImageType.PROFILE);
            admin.setImagePath(image);
        }

        adminRepository.save(admin);
    }

    @Override
    public AdminResponse getAdminByAuthId(String authId) {
        Admin admin = adminRepository.getAdminByAuthId(authId).orElseThrow(() -> {
            log.warn("Auth Id: {} - Admin Not Found", authId);
            return new NotFoundException("Admin Not Found");
        });
        admin.setImagePath(admin.getImagePath());
        return new AdminResponse(admin);
    }
}