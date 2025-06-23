package com.alex.banking.service.app.controller;

import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/account")
public class BankController {
    private BankUserDataBaseInterface bankUserDataBaseConnector;

    public BankController(BankUserDataBaseInterface bankUserDataBaseConnector) {
        this.bankUserDataBaseConnector = bankUserDataBaseConnector;
    }



    @GetMapping("/info/{username}")
    public ResponseEntity<BankUser> getAccountInfo(@PathVariable String username) {
        Optional<BankUser> accountInfo = bankUserDataBaseConnector.getUserForInfo(username);
        if (accountInfo.isPresent()) {

            return ResponseEntity.ok(accountInfo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/users/with/transactions/{username}")
    public ResponseEntity<List<BankUser>> getAllUsersWithTransactions(@PathVariable String username) {

        return bankUserDataBaseConnector.getAllBankUsersByAccountName(username);
    }




    @PutMapping("/change/password/name/{username}/old/{oldPassword}/new/{newPassword}")
    public ResponseEntity<Void> changePassword(@PathVariable String username,
                                               @PathVariable String oldPassword,
                                               @PathVariable String newPassword) {


        return bankUserDataBaseConnector.changePassword(username,oldPassword,newPassword);
    }










}
