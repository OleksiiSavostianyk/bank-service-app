package com.alex.banking.service.app.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity(name = "transactions")
@ToString
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long paymentID;

    @Column(name = "sender_id")
    private long senderID;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "sender_invoice")
    private long senderInvoice;

    @Column(name = "recipient_username")
    private String recipientUsername;

    @Column(name = "recipient_Invoice")
    private long recipientInvoice;

    @Column(name = "money")
    private double money;

    @Column(name = "date")
    private String date;

}
