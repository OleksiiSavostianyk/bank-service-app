package com.alex.bankingservicesapp.config;


import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserSecurityService implements UserDetailsService {

private UserRepository userRepository;

public UserSecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
        BankUser user = userRepository.findByAccountName(username);
        return new UserSecurityDetails(user);
    }catch (Exception e){
        System.out.println("User not found: " + username);
        throw new UsernameNotFoundException(e.getMessage());
    }
    }
}
