package com.alex.bankingservicesapp.service;


import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankUserService implements BankUserServiceInterface {

    private UserRepository userRepository;


    @Autowired
    public BankUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public BankUser createBankUser(BankUser bankUser) {
     bankUser =  userRepository.save(bankUser);
        return bankUser;
    }

    @Override
    public boolean deleteBankUser(BankUser bankUser) {
        userRepository.delete(bankUser);
        return false;
    }

    @Override
    public BankUser findByName(String name) {
        return userRepository.findByAccountName(name);
    }
}
