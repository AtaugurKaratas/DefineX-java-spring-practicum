package com.definex.credit.service.impl;

import com.definex.credit.dto.CreditRatingDto;
import com.definex.credit.dto.request.AdminInfoUpdateRequest;
import com.definex.credit.dto.request.AuthAdminRegister;
import com.definex.credit.dto.request.AuthEmployeeRegister;
import com.definex.credit.exception.NotFoundException;
import com.definex.credit.model.Admin;
import com.definex.credit.model.Auth;
import com.definex.credit.util.ImageProcessing;
import com.definex.credit.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private AuthServiceImpl authService;

    @Mock
    private CreditRatingServiceImpl creditRatingService;

    @Mock
    private ImageProcessing imageConvert;

    @Test
    void ShouldSaveAdminWhenInputsAreCorrect() {
        AuthAdminRegister authAdminRegister = new AuthAdminRegister("12345678901"
                , "Ataugur"
                , "Karatas"
                , "ataugurkaratas@gmail.com"
                , LocalDate.of(1998, 6, 4)
                , "905405404040");


        Auth auth = new Auth("id"
                , "12345678901"
                , "ataugurkaratas@gmail.com"
                , "password"
                , "ROLE_ADMIN"
                , true
                , true);

        when(authService.addUserAdmin(authAdminRegister)).thenReturn(auth);
        adminService.saveAdmin(authAdminRegister);

        Admin admin = new Admin("Ataugur"
                , "Karatas"
                , "905405404040"
                , "profile.jpeg"
                , LocalDate.of(1998, 6, 4)
                , auth);

        verify(adminRepository).save(admin);
    }

    @Test
    void ShouldSaveEmployee(){
        AuthEmployeeRegister authEmployeeRegister =
                new AuthEmployeeRegister("1111111111"
                ,"Ataugur"
                ,"Karatas"
                ,"ataugurkaratas@gmail.com"
                ,LocalDate.of(1998,6,4)
                ,"905405404040");
        adminService.saveEmployee(authEmployeeRegister);
    }

    @Test
    void ShouldSaveCurrentCreditRating(){
        CreditRatingDto creditRatingDto = new CreditRatingDto(25,25,25,25,250);
        adminService.saveCurrentCreditRating(creditRatingDto);
    }

    @Test
    void ShouldUpdateAdminInfoWhenInputsAreCorrect() {
        AdminInfoUpdateRequest adminInfoUpdateRequest = new AdminInfoUpdateRequest("id"
                , "Ataugur"
                , "Karatas"
                , "905425424242"
                , LocalDate.of(1998, 6, 4)
                , "profile.jpeg");

        Auth auth = new Auth();

        Admin admin = new Admin("Ataugur"
                , "Karatas"
                , "905405404040"
                , "profile.jpeg"
                , LocalDate.of(1998, 6, 4)
                , auth);

        when(adminRepository.findById(adminInfoUpdateRequest.adminId())).thenReturn(Optional.of(admin));
        adminService.updateAdminInfo(adminInfoUpdateRequest);

        verify(adminRepository).save(admin);
    }

    @Test
    void ShouldReturnAdminResponseWhenAuthId(){
        String authId = "id";

        Auth auth = new Auth();
        Admin admin = new Admin("Ataugur"
                , "Karatas"
                , "905405404040"
                , "profile.jpeg"
                , LocalDate.of(1998, 6, 4)
                , auth);

        when(adminRepository.getAdminByAuthId(authId)).thenReturn(Optional.of(admin));

        adminService.getAdminByAuthId(authId);
    }

    @Test
    void ShouldThrowExceptionWhenUpdateAdminInfo(){
        AdminInfoUpdateRequest request = new AdminInfoUpdateRequest("id"
                , "Ataugur"
                , "Karatas"
                , "905425424242"
                , LocalDate.of(1998, 6, 4)
                , "profile.jpeg");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                adminService.updateAdminInfo(request));

        assertEquals("Admin Not Found", exception.getMessage());
    }

    @Test
    void ShouldThrowExceptionWhenGetAdminByAuthId(){
        String authId = "id";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                adminService.getAdminByAuthId(authId));

        assertEquals("Admin Not Found", exception.getMessage());
    }
}