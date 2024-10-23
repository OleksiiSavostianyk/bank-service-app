package com.alex.bankingservicesapp.controller;

import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.models.json_classes.LoginRequest;
import com.alex.bankingservicesapp.models.json_classes.LoginResponse;
import com.alex.bankingservicesapp.service.authorizationService.AuthServiceInterface;
import com.alex.bankingservicesapp.service.userService.BankUserDataBaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    private BankUserDataBaseInterface bankUserDataBaseConnector;
    private AuthServiceInterface authService;

    @Autowired
    public AuthorizationController(BankUserDataBaseInterface bankUserDataBaseConnector, AuthServiceInterface authService) {
        this.bankUserDataBaseConnector = bankUserDataBaseConnector;
        this.authService = authService;
    }


    @PostMapping("/registration")
    public ResponseEntity<String> registerUser2(@RequestBody BankUser user) {
        ResponseEntity<String> response;
        try {
           response =   bankUserDataBaseConnector.createBankUser(user);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }




    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<String> userName = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (userName.isPresent()) {
            return ResponseEntity.ok(userName.get());  // Возвращаем имя пользователя в случае успеха
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 401 Unauthorized, если аутентификация не удалась
        }
    }



}










































