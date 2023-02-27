package com.definex.credit.annotation.validation;

import com.definex.credit.annotation.UniqueIdentityNumber;
import com.definex.credit.model.Auth;
import com.definex.credit.repository.AuthRepository;
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
