package com.alex.bankingservicesapp.exception;

public class NoSuchPaymentException extends RuntimeException {
    public NoSuchPaymentException(String message) {
        super(message);
    }
}
