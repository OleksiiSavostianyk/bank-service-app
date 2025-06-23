package com.alex.banking.service.app.service.authorizationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements AuthServiceInterface {
    @Value("${jwt.secret}")
    private  String secretKey;

    private BankUserDataBaseInterface bankUserDataBaseConnector;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public AuthService(BankUserDataBaseInterface bankUserDataBaseConnector) {
        this.bankUserDataBaseConnector = bankUserDataBaseConnector;
    }


    @Override
    public Optional<String> authenticate(String name, String password) {
        Optional<BankUser> user = bankUserDataBaseConnector.findByAccountName(name);
        if (user.isPresent() && bCryptPasswordEncoder.matches(password,user.get().getPassword())) {
            return Optional.of(user.get().getAccountName());
        }

        return Optional.empty();
    }

}
