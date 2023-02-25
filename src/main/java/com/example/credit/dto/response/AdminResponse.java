package com.example.credit.dto.response;

import com.example.credit.model.Admin;

import java.time.LocalDate;

public record AdminResponse(
        String adminId,
        String name,
        String surname,
        String phoneNumber,
        LocalDate birthDate,
        String imagePath) {
    public AdminResponse(Admin admin) {
        this(admin.getId()
                , admin.getName()
                , admin.getSurname()
                , admin.getPhoneNumber()
                , admin.getBirthDate()
                , ("/images/" + admin.getImagePath()));
    }
}