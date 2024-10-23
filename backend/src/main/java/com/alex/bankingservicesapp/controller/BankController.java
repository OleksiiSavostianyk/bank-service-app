package com.alex.bankingservicesapp.controller;

import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.service.userService.BankUserDataBaseInterface;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/account")
//@CrossOrigin(origins = "http://localhost:3000")
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








}
