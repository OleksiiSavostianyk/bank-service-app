package com.alex.bankingservicesapp.repository;

import com.alex.bankingservicesapp.models.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BankUser, Long> {

    public BankUser findByAccountName(String username);
    public BankUser getById(Long id);
}
