package com.alex.bankingservicesapp.service;

import com.alex.bankingservicesapp.models.BankUser;

public interface BankUserPaymentInterface {
    public boolean transfer(long sum, BankUser bankUser);
    public boolean withdraw(long sum, BankUser bankUser);
    public boolean deposit(long sum, BankUser bankUser);
}
