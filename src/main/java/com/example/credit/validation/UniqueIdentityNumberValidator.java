package com.example.credit.validation;

import com.example.credit.annotation.UniqueIdentityNumber;
import com.example.credit.model.Auth;
import com.example.credit.repository.AuthRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueIdentityNumberValidator implements ConstraintValidator<UniqueIdentityNumber, String> {

    private final AuthRepository authRepository;

    public UniqueIdentityNumberValidator(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public boolean isValid(String identityNumber, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Auth> auth = authRepository.findByIdentityNumber(identityNumber);
        if (auth.isEmpty())
            return true;
        return false;
    }
}
