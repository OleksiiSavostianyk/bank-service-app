package com.alex.bankingservicesapp.service;

public class InvoiceGenerator {

    public static long getInvoice(){
        String invoiceNumber = "4477";
        String time = String.valueOf(System.currentTimeMillis()).substring(0, 8);
        invoiceNumber = invoiceNumber + time;
        return Long.parseLong(invoiceNumber);
    }
}
