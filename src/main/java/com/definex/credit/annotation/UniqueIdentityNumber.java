package com.definex.credit.annotation;

import com.definex.credit.annotation.validation.UniqueIdentityNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {UniqueIdentityNumberValidator.class}
)
public @interface UniqueIdentityNumber {
    String message() default "{jakarta.validation.constraints.UniqueIdentityNumber.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
