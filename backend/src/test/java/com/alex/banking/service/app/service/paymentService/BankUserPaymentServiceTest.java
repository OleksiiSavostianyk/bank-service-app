package com.alex.banking.service.app.service.paymentService;

import com.alex.banking.service.app.exception.UserNotFoundException;
import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.models.Payment;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankUserPaymentServiceTest {

    @Mock
    private BankUserDataBaseInterface bankUserDataBaseConnector;

    @Mock
    private PaymentServiceDB paymentRepository;

    @Mock
    private HandlerMapping handlerMapping;

    @InjectMocks
    private BankUserPaymentService bankUserPaymentService;

    private BankUser sender;
    private BankUser recipient;
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankUserPaymentService = new BankUserPaymentService(bankUserDataBaseConnector, paymentRepository, handlerMapping);

        sender = new BankUser();
        sender.setId(1L);
        sender.setAccountName("sender");
        sender.setBalance(100.0);
        sender.setRole("USER");
        sender.setInvoice(111111L);

        recipient = new BankUser();
        recipient.setId(2L);
        recipient.setAccountName("recipient");
        recipient.setBalance(50.0);
        recipient.setRole("USER");
        recipient.setInvoice(222222L);

        payment = new Payment();
        payment.setSenderName("sender");
        payment.setRecipientUsername("recipient");
        payment.setMoney(30.0);
        payment.setSenderInvoice(sender.getInvoice());
        payment.setRecipientInvoice(recipient.getInvoice());
        payment.setDate(LocalDate.now().toString());
    }

    @Test
    public void transfer_successful() {
        when(bankUserDataBaseConnector.findByAccountName("sender"))
                .thenReturn(Optional.of(sender));
        when(bankUserDataBaseConnector.findByAccountName("recipient"))
                .thenReturn(Optional.of(recipient));


        ResponseEntity<String> response = bankUserPaymentService.transfer(payment);



        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment transferred successfully.", response.getBody());
    }

    @Test
    public void transfer_senderNotFound() {
        when(bankUserDataBaseConnector.findByAccountName("sender"))
                .thenReturn(Optional.empty());


        ResponseEntity<String> response = bankUserPaymentService.transfer(payment);



        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void transfer_recipientNotFound() {
        when(bankUserDataBaseConnector.findByAccountName("sender"))
                .thenReturn(Optional.of(sender));
        when(bankUserDataBaseConnector.findByAccountName("recipient"))
                .thenThrow(new UserNotFoundException("Recipient not found"));



        ResponseEntity<String> response = bankUserPaymentService.transfer(payment);




        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("User not found"));
    }

    @Test
    public void transfer_notEnoughMoney() {
        sender.setBalance(10.0);

        when(bankUserDataBaseConnector.findByAccountName("sender"))
                .thenReturn(Optional.of(sender));
        when(bankUserDataBaseConnector.findByAccountName("recipient"))
                .thenReturn(Optional.of(recipient));



        ResponseEntity<String> response = bankUserPaymentService.transfer(payment);



        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Not enough money"));
    }



    @Test
    public void transfer_genericException() {
        when(bankUserDataBaseConnector.findByAccountName("sender"))
                .thenReturn(Optional.of(sender));
        when(bankUserDataBaseConnector.findByAccountName("recipient"))
                .thenThrow(new RuntimeException("Unexpected error"));


        ResponseEntity<String> response = bankUserPaymentService.transfer(payment);



        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Unexpected error"));
    }











    @Test
    void withdraw_success() {
        when(bankUserDataBaseConnector.findById(sender.getId())).thenReturn(sender);

        boolean result = bankUserPaymentService.withdraw(50.0, sender);

        assertTrue(result);
        assertEquals(50.0, sender.getBalance());
        verify(bankUserDataBaseConnector).updateBankUser(sender);
    }

    @Test
    void withdraw_notEnoughFunds() {
        when(bankUserDataBaseConnector.findById(sender.getId())).thenReturn(sender);

        boolean result = bankUserPaymentService.withdraw(150.0, sender);

        assertFalse(result);
        verify(bankUserDataBaseConnector, never()).updateBankUser(any());
    }

    @Test
    void withdraw_exceptionThrown() {
        when(bankUserDataBaseConnector.findById(sender.getId()))
                .thenThrow(new RuntimeException("Database error"));

        boolean result = bankUserPaymentService.withdraw(30.0, sender);

        assertFalse(result);
        verify(bankUserDataBaseConnector, never()).updateBankUser(any());
    }





    @Test
    void deposit_success() {
        sender.setBalance(100.0);
        when(bankUserDataBaseConnector.findById(sender.getId())).thenReturn(sender);

        boolean result = bankUserPaymentService.deposit(50.0, sender);

        assertTrue(result);
        assertEquals(150.0, sender.getBalance());
    }

    @Test
    void deposit_exceptionThrown() {
        when(bankUserDataBaseConnector.findById(sender.getId()))
                .thenThrow(new RuntimeException("DB error"));

        boolean result = bankUserPaymentService.deposit(50.0, sender);

        assertFalse(result);
    }

    @Test
    void deposit_zeroAmount() {
        sender.setBalance(100.0);
        when(bankUserDataBaseConnector.findById(sender.getId())).thenReturn(sender);

        boolean result = bankUserPaymentService.deposit(0.0, sender);

        assertTrue(result);
        assertEquals(100.0, sender.getBalance());
    }


    @Test
    void deposit_negativeAmount() {
        sender.setBalance(100.0);
        when(bankUserDataBaseConnector.findById(sender.getId())).thenReturn(sender);

        boolean result = bankUserPaymentService.deposit(-30.0, sender);

        assertTrue(result);
        assertEquals(70.0, sender.getBalance());
    }







    @Test
    void getAllPayments_success() {
        List<Payment> mockPayments = List.of(payment);

        when(bankUserDataBaseConnector.findByAccountName(sender.getAccountName()))
                .thenReturn(Optional.of(sender));
        when(paymentRepository.getAllPayments(sender.getId()))
                .thenReturn(mockPayments);

        ResponseEntity<List<Payment>> response = bankUserPaymentService.getAllPayments(sender.getAccountName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(payment, response.getBody().get(0));
    }

    @Test
    void getAllPayments_userNotFound() {
        when(bankUserDataBaseConnector.findByAccountName("noneExist"))
                .thenReturn(Optional.empty());

        ResponseEntity<List<Payment>> response = bankUserPaymentService.getAllPayments("noneExist");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

}