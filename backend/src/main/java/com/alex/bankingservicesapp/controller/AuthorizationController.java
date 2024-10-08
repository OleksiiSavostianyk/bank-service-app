package com.alex.bankingservicesapp.controller;

import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.service.InvoiceGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {


    @PostMapping("/registration")
    public ResponseEntity<String> registerUser2(@RequestBody BankUser user) {
        user.setInvoice(InvoiceGenerator.getInvoice());
        user.setRole("USER");
        System.out.println(user);
        return ResponseEntity.ok("Successfully registered user");
    }

    @GetMapping("/check/authentication")
    public ResponseEntity<Void> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/login")
    public String login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login.html";
        }


        return"redirect:localhost:3000";

    }



}
