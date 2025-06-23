package com.alex.bankingservicesapp.service.userService;

import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.models.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BankUserDataBaseInterface {



    public ResponseEntity<String> createBankUser(BankUser bankUser);
    public boolean deleteBankUser(BankUser bankUser);
    public Optional<BankUser> findByAccountName(String name);
    public Optional<BankUser> findByInvoice(long invoice);
    public BankUser findById(long id);
    public boolean updateBankUser(BankUser bankUser);
    public Optional<BankUser> getUserForInfo(String accountName);
    public ResponseEntity<List<BankUser>> getAllBankUsersByAccountName(String accountName);
    public ResponseEntity<Void> changePassword( String username, String oldPassword, String newPassword);



}
