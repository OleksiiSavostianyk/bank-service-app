package com.alex.banking.service.app.exception;

public class NoSuchPaymentException extends RuntimeException {
    public NoSuchPaymentException(String message) {
        super(message);
    }
}
