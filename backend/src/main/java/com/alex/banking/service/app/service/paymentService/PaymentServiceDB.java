package com.alex.bankingservicesapp.service.paymentService;

import com.alex.bankingservicesapp.models.Payment;

import java.util.List;

public interface PaymentServiceDB {
    public Payment findPaymentById(long id);
    public Payment savePayment(Payment payment);
    public List<Payment> getAllPayments(Long id);
}
