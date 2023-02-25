package com.example.credit.dto.request;

import com.example.credit.annotation.UniqueEmail;
import com.example.credit.annotation.UniqueIdentityNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRegisterRequest(
        @NotEmpty
        @UniqueIdentityNumber
        @Size(min = 11, max = 11, message = "{jakarta.validation.constraints.Size.IdentityNumber.message}")
        String identityNumber,
        @NotEmpty
        @UniqueEmail
        @Email
        String email,
        @NotEmpty
        @Size(min = 8, max = 70, message = "{jakarta.validation.constraints.Size.Password.message}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{jakarta.validation.constraints.Pattern.Regex.message}")
        String password) {
}