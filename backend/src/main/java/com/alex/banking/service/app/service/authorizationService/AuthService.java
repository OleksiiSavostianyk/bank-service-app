package com.alex.bankingservicesapp.service.authorizationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.service.userService.BankUserDataBaseInterface;
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


    private  String generateToken(BankUser user) {
        long expirationTime = 1000 * 60 * 60; // 1 час
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(user.getAccountName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey) // Используй свой секретный ключ
                .compact();
    }
}
