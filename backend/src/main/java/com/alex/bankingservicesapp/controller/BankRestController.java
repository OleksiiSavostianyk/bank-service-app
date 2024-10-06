package com.alex.bankingservicesapp.controller;


import com.alex.bankingservicesapp.models.BankUser;
import com.alex.bankingservicesapp.service.BankUserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestController  {

    private BankUserServiceInterface bankUserService;

    @Autowired
    public BankRestController(BankUserServiceInterface bankUserService) {
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

        BankUser bankUser = new BankUser();
        bankUser.setFirstName("Alex");
        bankUser.setLastName("LastName");
        bankUser.setEmail("alex@gmail.com");
        bankUser.setPassword("password");
        bankUser.setAccountName("UniqueName");
        bankUser.setBalance(2500);

       BankUser inputUser = bankUserService.createBankUser(bankUser);

        return inputUser.toString();
    }
    @GetMapping("/endpoint")
    public ResponseEntity<String> handleGet() {
        return ResponseEntity.ok("GET method supported");
    }


}
