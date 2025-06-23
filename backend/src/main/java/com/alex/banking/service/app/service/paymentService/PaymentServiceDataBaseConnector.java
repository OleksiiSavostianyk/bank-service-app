package com.alex.bankingservicesapp.service.paymentService;

import com.alex.bankingservicesapp.exception.NoSuchPaymentException;
import com.alex.bankingservicesapp.models.Payment;
import com.alex.bankingservicesapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Payment findPaymentById(long id) throws NoSuchPaymentException {
      Optional<Payment> payment = paymentRepository.findById(id);
      if (payment.isEmpty()) { throw new NoSuchPaymentException("Payment not found, id: " + id);
      }
     return payment.get();
    }

    @Override
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
