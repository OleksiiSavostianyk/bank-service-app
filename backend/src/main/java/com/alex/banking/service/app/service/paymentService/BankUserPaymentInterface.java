package com.alex.banking.service.app.service.paymentService;

import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.models.Payment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankUserPaymentInterface {
    ResponseEntity<String> transfer(Payment payment);
    boolean withdraw(double sum, BankUser bankUser);
    boolean deposit(double sum, BankUser bankUser);
    ResponseEntity<List<Payment>> getAllPayments(String username);
}
