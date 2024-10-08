package com.alex.bankingservicesapp.controller;


import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.models.Payment;
import com.alex.bankingservicesapp.service.BankUserDataBaseInterface;
import com.alex.bankingservicesapp.service.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class BankRestController  {

    private BankUserDataBaseInterface bankUserService;

    @Autowired
    public BankRestController(BankUserDataBaseInterface bankUserService) {
        this.bankUserService = bankUserService;
    }

    @RequestMapping("/delete")
    public String delete(){

//        BankUser bankUser = bankUserService.findByName("Alex");
//        System.out.println(bankUser);
        BankUser bankUser = bankUserService.findByName("UniqueName");
        bankUserService.deleteBankUser(bankUser);
        return "deleted";
    }


    @RequestMapping("/get/response")
    public String getResponse() {
//
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("_-----------------------------------");
//        System.out.println(userDetails.getUsername());
//        System.out.println("_-----------------------------------");
//
//        BankUser bankUser = new BankUser();
//        bankUser.setRole("USER");
//        bankUser.setFirstName("Alex");
//        bankUser.setLastName("LastName");
//        bankUser.setEmail("alex@gmail.com");
//        bankUser.setPassword("password");
//        bankUser.setAccountName("UniqueName");
//        bankUser.setInvoice(InvoiceGenerator.getInvoice());
//        bankUser.setBalance(2500);
//
//        System.out.println(bankUser);
//
//       BankUser inputUser = bankUserService.createBankUser(bankUser);
//
//        return inputUser.toString();

        return "hello";
    }





    @PutMapping("/transfer/money")
    public ResponseEntity<String> handleGet(@RequestBody Payment payment) {


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());



        return ResponseEntity.ok("GET method supported");
    }


}
