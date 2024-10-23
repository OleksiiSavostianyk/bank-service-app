package com.alex.bankingservicesapp.service;

public class InvoiceGenerator {

    public static long getInvoice(String name){
        String invoiceNumber = "4477";
         Integer nameHash = name.hashCode();
        String time = String.valueOf(System.currentTimeMillis());
        invoiceNumber = invoiceNumber
                + time.substring(time.length()-4,time.length())
                + String.valueOf(nameHash.hashCode()).substring(1, 5);
        return Long.parseLong(invoiceNumber);
    }
}
