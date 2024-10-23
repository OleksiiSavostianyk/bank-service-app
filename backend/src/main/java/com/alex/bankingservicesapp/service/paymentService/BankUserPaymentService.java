package com.alex.bankingservicesapp.service.paymentService;

import com.alex.bankingservicesapp.exception.NotEnoughMoneyException;
import com.alex.bankingservicesapp.exception.UserNotFoundException;
import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.models.Payment;
import com.alex.bankingservicesapp.service.userService.BankUserDataBaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BankUserPaymentService implements BankUserPaymentInterface {


    private final HandlerMapping resourceHandlerMapping;
    private BankUserDataBaseInterface bankUserDataBaseConnector;
    private PaymentServiceDB paymentRepository;

    @Autowired
    public BankUserPaymentService(BankUserDataBaseInterface bankUserDataBase, PaymentServiceDB paymentRepository, @Qualifier("resourceHandlerMapping") HandlerMapping resourceHandlerMapping) {
        this.bankUserDataBaseConnector = bankUserDataBase;
        this.paymentRepository = paymentRepository;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }


    @Override
    public ResponseEntity<String> transfer(Payment payment) {

        try {
            BankUser recipient = findRecipient(payment);
            Optional<BankUser> sender = bankUserDataBaseConnector.findByAccountName(payment.getSenderName());

            if (sender.isEmpty()) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }

            BankUser senderBankUser = sender.get();
            double newBalance = senderBankUser.getBalance() - payment.getMoney();
            if ( newBalance < 0){
                throw new NotEnoughMoneyException("Not enough money for  user " + sender);
            }

            senderBankUser.setBalance(newBalance);
            recipient.setBalance(recipient.getBalance() + payment.getMoney());

            savePayment(payment,senderBankUser,recipient);
            bankUserDataBaseConnector.updateBankUser(senderBankUser);
            bankUserDataBaseConnector.updateBankUser(recipient);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>("User not found. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (NotEnoughMoneyException e){
            return new ResponseEntity<>("Not enough money. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok("Payment transferred successfully.");
    }






    @Override
    public boolean withdraw(double sum, BankUser bankUser) {
        try {
            BankUser sender = bankUserDataBaseConnector.findById(bankUser.getId());
            double newBalance = sender.getBalance() - sum;
            if (newBalance < 0) {
                return false;
            }
            sender.setBalance(newBalance);
            bankUserDataBaseConnector.updateBankUser(sender);
        }catch (Exception e){
            return false;
        }

        return true;
    }





    @Override
    public boolean deposit(double sum, BankUser bankUser) {
        try {
            BankUser recipient = bankUserDataBaseConnector.findById(bankUser.getId());
            recipient.setBalance(recipient.getBalance() + sum);
        }catch (Exception e){
            return false;
        }
        return true;
    }





    @Override
    public ResponseEntity<List<Payment>> getAllPayments(String username) {
        Optional<BankUser> user = bankUserDataBaseConnector.findByAccountName(username);
        if (user.isEmpty()) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        List<Payment> payments = paymentRepository.getAllPayments(user.get().getId());
        return ResponseEntity.ok(payments);
    }





    private BankUser findRecipient(Payment payment) {
        String id;
        Optional<BankUser> recipient ;
        if (payment.getRecipientUsername() != null) {
            recipient = bankUserDataBaseConnector.findByAccountName(payment.getRecipientUsername());
            id = payment.getRecipientUsername();
        } else  {
            recipient = bankUserDataBaseConnector.findByInvoice(payment.getRecipientInvoice());
            id = String.valueOf(payment.getRecipientInvoice());
        }

        return recipient.orElseThrow(() -> new UserNotFoundException("Recipient with id: " + id + " not found"));
    }

    public void savePayment(Payment payment, BankUser sender,BankUser receiver) {
        payment.setSenderID(sender.getId());
        payment.setSenderName(sender.getAccountName());
        payment.setSenderInvoice(sender.getInvoice());
        payment.setRecipientUsername(receiver.getAccountName());
        payment.setRecipientInvoice(receiver.getInvoice());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm");
        String formattedDate = now.format(formatter);

        payment.setDate(formattedDate);

        paymentRepository.savePayment(payment);
    }
}
