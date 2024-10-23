package com.alex.bankingservicesapp.exception;

import com.alex.bankingservicesapp.models.BankUser;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
