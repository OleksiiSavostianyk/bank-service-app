package com.alex.banking.service.app.service.paymentService;

import com.alex.banking.service.app.exception.NoSuchPaymentException;
import com.alex.banking.service.app.models.Payment;
import com.alex.banking.service.app.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PaymentServiceDataBaseConnectorTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceDataBaseConnector paymentService;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = new Payment();
        testPayment.setPaymentID(1L);
        testPayment.setSenderName("sender");
        testPayment.setRecipientUsername("recipient");
        testPayment.setMoney(50.0);
        testPayment.setDate("2024-01-01");
    }

    @Test
    void findPaymentById_success() throws NoSuchPaymentException {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        Payment result = paymentService.findPaymentById(1L);

        assertEquals(testPayment, result);
        verify(paymentRepository).findById(1L);
    }

    @Test
    void findPaymentById_notFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchPaymentException.class, () -> paymentService.findPaymentById(1L));
        verify(paymentRepository).findById(1L);
    }



    @Test
    void savePayment_success() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        Payment saved = paymentService.savePayment(testPayment);

        assertNotNull(saved);
        assertEquals(testPayment, saved);
        verify(paymentRepository).save(testPayment);
    }


}