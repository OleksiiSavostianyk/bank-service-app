package com.alex.bankingservicesapp.controller;


import com.alex.bankingservicesapp.models.Payment;
import com.alex.bankingservicesapp.service.userService.BankUserDataBaseInterface;
import com.alex.bankingservicesapp.service.paymentService.BankUserPaymentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class BankPaymentController {

    private BankUserDataBaseInterface bankUserService;
    private BankUserPaymentInterface bankUserPaymentService;

    @Autowired
    public BankPaymentController(BankUserDataBaseInterface bankUserService, BankUserPaymentInterface bankUserPaymentService) {
        this.bankUserService = bankUserService;
        this.bankUserPaymentService = bankUserPaymentService;
    }


    @PostMapping("/transfer/money")
    public ResponseEntity<String> transferMoney(@RequestBody Payment payment ) {

        return bankUserPaymentService.transfer(payment);
    }

    @GetMapping("/all/payments/{username}")
    private ResponseEntity<List<Payment>> getAllPayments(@PathVariable String username) {
        return bankUserPaymentService.getAllPayments(username);
    }








}
