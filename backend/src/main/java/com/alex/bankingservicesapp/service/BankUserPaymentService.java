package com.alex.bankingservicesapp.service;

import com.alex.bankingservicesapp.models.BankUser;

public class BankUserPaymentService implements BankUserPaymentInterface {
    @Override
    public boolean transfer(long sum, BankUser bankUser) {
        return false;
    }

    @Override
    public boolean withdraw(long sum, BankUser bankUser) {
        return false;
    }

    @Override
    public boolean deposit(long sum, BankUser bankUser) {
        return false;
    }
}
