package com.definex.credit.controller;

import com.definex.credit.service.impl.AdminServiceImpl;
import com.definex.credit.dto.request.AdminInfoUpdateRequest;
import com.definex.credit.dto.request.AuthAdminRegister;
import com.definex.credit.dto.request.AuthEmployeeRegister;
import com.definex.credit.dto.response.AdminResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/employee/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> saveEmployee(@Valid @RequestBody AuthEmployeeRegister authEmployeeRegister){
        adminService.saveEmployee(authEmployeeRegister);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> saveAdmin(@Valid @RequestBody AuthAdminRegister authAdminRegister){
        adminService.saveAdmin(authAdminRegister);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @PutMapping("/admin-info-update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateAdminInfo(@Valid @RequestBody AdminInfoUpdateRequest adminInfoUpdateRequest){
        adminService.updateAdminInfo(adminInfoUpdateRequest);
        return new ResponseEntity<>("Admin Info Updated", HttpStatus.OK);
    }

    @GetMapping("/{authId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AdminResponse getAdmin(@PathVariable String authId){
        return adminService.getAdminByAuthId(authId);
    }
}