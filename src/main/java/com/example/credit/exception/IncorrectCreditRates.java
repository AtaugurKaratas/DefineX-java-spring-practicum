package com.example.credit.exception;

public class IncorrectCreditRates extends RuntimeException {
    public IncorrectCreditRates(String message) {
        super(message);
    }
}
