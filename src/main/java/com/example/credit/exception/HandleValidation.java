package com.example.credit.exception;

import com.example.credit.error.ApiError;
import com.example.credit.error.ApiValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleValidation {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationError handleValidationException(MethodArgumentNotValidException exception) {
        ApiValidationError error = new ApiValidationError(400, "Validation Error");
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return error;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationError handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ApiValidationError(400, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception) {
        return new ApiError(404, exception.getMessage());
    }

    @ExceptionHandler(IncorrectCreditRates.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIncorrectCreditRates(IncorrectCreditRates exception) {
        return new ApiError(400, exception.getMessage());
    }

    @ExceptionHandler(MismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMismatchException(MismatchException exception) {
        return new ApiError(400, exception.getMessage());
    }
}
