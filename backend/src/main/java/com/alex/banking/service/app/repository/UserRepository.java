package com.alex.bankingservicesapp.repository;

import com.alex.bankingservicesapp.models.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<BankUser, Long> {

    public Optional<BankUser> findByAccountName(String username);
    public BankUser getById(Long id);
    public Optional<BankUser> findByInvoice(Long invoice);
    public List<BankUser> findAllByAccountNameIn(List<String> username);
}
