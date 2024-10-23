package com.alex.bankingservicesapp.service.userService;


import com.alex.bankingservicesapp.exception.UserNotFoundException;
import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.models.Payment;
import com.alex.bankingservicesapp.repository.UserRepository;
import com.alex.bankingservicesapp.service.InvoiceGenerator;
import com.alex.bankingservicesapp.service.RoleKeeper;
import com.alex.bankingservicesapp.service.authorizationService.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankUserDataBaseConnector implements BankUserDataBaseInterface {

    private UserRepository userRepository;
    private RoleKeeper roleKeeper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public BankUserDataBaseConnector(UserRepository userRepository,
                                     RoleKeeper roleKeeper,
                                     BCryptPasswordEncoder bCryptPasswordEncoder){
        this.roleKeeper = roleKeeper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

}
