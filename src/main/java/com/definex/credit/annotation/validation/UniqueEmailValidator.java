package com.definex.credit.annotation.validation;

import com.definex.credit.annotation.UniqueEmail;
import com.definex.credit.model.Auth;
import com.definex.credit.repository.AuthRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AuthRepository authRepository;

    public UniqueEmailValidator(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Auth> auth = authRepository.findByEmail(email);
        if (auth.isEmpty())
            return true;
        return false;
    }
}
