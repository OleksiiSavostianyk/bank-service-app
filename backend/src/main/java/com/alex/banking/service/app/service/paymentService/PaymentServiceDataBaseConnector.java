package com.alex.banking.service.app.service.paymentService;

import com.alex.banking.service.app.exception.NoSuchPaymentException;
import com.alex.banking.service.app.models.Payment;
import com.alex.banking.service.app.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceDataBaseConnector implements PaymentServiceDB{

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceDataBaseConnector(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment findPaymentById(long id) throws NoSuchPaymentException {
      Optional<Payment> payment = paymentRepository.findById(id);
      if (payment.isEmpty()) { throw new NoSuchPaymentException("Payment not found, id: " + id);
      }
     return payment.get();
    }

    @Override
    @Transactional
    public Payment savePayment(Payment payment) {
        payment.setDate(getDateNow());
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments(Long id) {
        return paymentRepository.findBySenderID(id);
    }




    private String getDateNow(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return now.format(formatter);
    }
}
