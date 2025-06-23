package com.alex.banking.service.app.controller;

import com.alex.banking.service.app.models.Payment;
import com.alex.banking.service.app.service.paymentService.BankUserPaymentInterface;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankPaymentControllerTest {

    @Mock
    private BankUserDataBaseInterface bankUserService;

    @Mock
    private BankUserPaymentInterface bankUserPaymentService;

    @InjectMocks
    private BankPaymentController bankPaymentController;

    @Test
    void transferMoney_shouldReturnSuccessResponse() {

        Payment payment = new Payment();
        when(bankUserPaymentService.transfer(payment))
                .thenReturn(ResponseEntity.ok("Transfer successful"));


        ResponseEntity<String> response = bankPaymentController.transferMoney(payment);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transfer successful", response.getBody());
    }

    @Test
    void getAllPayments_shouldReturnPaymentList() {

        String username = "john";
        List<Payment> payments = List.of(new Payment(), new Payment());
        when(bankUserPaymentService.getAllPayments(username))
                .thenReturn(ResponseEntity.ok(payments));


        ResponseEntity<List<Payment>> response = bankPaymentController.getAllPayments(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

}