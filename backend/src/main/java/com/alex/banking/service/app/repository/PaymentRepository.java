package com.alex.bankingservicesapp.repository;

import com.alex.bankingservicesapp.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Optional<Payment> findByPaymentID(Long id);
    public List<Payment> findBySenderID(Long id);
}
