package com.alex.banking.service.app.service.userService;


import com.alex.banking.service.app.exception.UserNotFoundException;
import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.models.Payment;
import com.alex.banking.service.app.repository.PaymentRepository;
import com.alex.banking.service.app.repository.UserRepository;
import com.alex.banking.service.app.service.InvoiceGenerator;
import com.alex.banking.service.app.service.RoleKeeper;
import com.alex.banking.service.app.service.authorizationService.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankUserDataBaseConnector implements BankUserDataBaseInterface {

    private UserRepository userRepository;
    private RoleKeeper roleKeeper;
    private PaymentRepository paymentRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public BankUserDataBaseConnector(UserRepository userRepository,
                                     RoleKeeper roleKeeper,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     PaymentRepository paymentRepository){
        this.roleKeeper = roleKeeper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.paymentRepository = paymentRepository;
    }




    @Override
    public ResponseEntity<String> createBankUser(BankUser bankUser) {


        if (userRepository.findByAccountName(bankUser.getAccountName()) != null) {
            return new ResponseEntity<>("Account name already used", HttpStatus.CONFLICT);
        }

        String clearedPassword = bankUser.getPassword();
        bankUser.setInvoice(InvoiceGenerator.getInvoice(bankUser.getAccountName()));
        bankUser.setRole(roleKeeper.getROLE_USER());
        bankUser.setBalance(0.0);
        bankUser.setPassword(bCryptPasswordEncoder.encode(bankUser.getPassword()));
        bankUser = userRepository.save(bankUser);

        return ResponseEntity.ok("{\"status\":\"success\"}");
    }





    @Override
    public boolean deleteBankUser(BankUser bankUser) {
        userRepository.delete(bankUser);
        return false;
    }




    @Override
    public Optional<BankUser> findByAccountName(String name) {
        return userRepository.findByAccountName(name);
    }




    @Override
    public Optional<BankUser> findByInvoice(long invoice) {
        return userRepository.findByInvoice(invoice);
    }




    @Override
    public BankUser findById(long id) {

        Optional<BankUser> bankUser = userRepository.findById(id);
        if (bankUser.isEmpty()) {throw new  UserNotFoundException("User:id" + id +"not found");}


        return bankUser.get();
    }





    @Override
    public boolean updateBankUser(BankUser bankUser) {
        BankUser updatedUser = userRepository.save(bankUser);

        return updatedUser != null;
    }





    @Override
    public Optional<BankUser> getUserForInfo(String accountName) {
      Optional<BankUser> bankUser =  userRepository.findByAccountName(accountName);
     if (bankUser.isPresent()){
       BankUser user = bankUser.get();
       user.setPassword("");
       return Optional.of(user);
     }
      return bankUser;
    }




    @Override
    public ResponseEntity<List<BankUser>> getAllBankUsersByAccountName(String accountName) {

        Optional<BankUser> user = userRepository.findByAccountName(accountName);
        if (user.isEmpty()){ return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        List<Payment> payments = paymentRepository.findBySenderID(user.get().getId());

        List<BankUser> bankUsers = getAllBankUsersByPayments(payments);

        if (bankUsers.isEmpty()){ return  ResponseEntity.ok().build(); }
        return  ResponseEntity.ok(bankUsers);
    }




    @Override
    public ResponseEntity<Void> changePassword(String username, String oldPassword, String newPassword) {
        Optional<BankUser> bankUser = userRepository.findByAccountName(username);
        if (bankUser.isEmpty()){ return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        if (!BCryptPasswordEncoder.matches(oldPassword, bankUser.get().getPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
        BankUser user = bankUser.get();
        user.setPassword(BCryptPasswordEncoder.encode(newPassword));

        userRepository.save(user);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok().build();
    }








    protected List<BankUser> getAllBankUsersByPayments(List<Payment> payments) {

        Set<String> accountNames = payments.stream()
                .map(Payment::getRecipientUsername)
                .collect(Collectors.toSet());


        List<BankUser> bankUsers = userRepository.findAllByAccountNameIn(new ArrayList<>(accountNames));
      bankUsers.forEach(bankUser -> {bankUser.setPassword(""); bankUser.setBalance(0.0);});
        return bankUsers;
    }






}
