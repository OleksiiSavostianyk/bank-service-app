package com.alex.bankingservicesapp.models;


import lombok.Data;

@Data
public class Payment {

    private int payment_id;
    private BankUser bank_user;
    private long invoiceForPayment;
    private long price;

}
