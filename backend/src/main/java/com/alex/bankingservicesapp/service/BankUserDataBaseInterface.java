package com.alex.bankingservicesapp.service;

import com.alex.bankingservicesapp.models.BankUser;

public interface BankUserDataBaseInterface {


    public BankUser createBankUser(BankUser bankUser);
    public boolean deleteBankUser(BankUser bankUser);
    public BankUser findByName(String name);


}
