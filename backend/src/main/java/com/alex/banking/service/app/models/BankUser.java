package com.alex.banking.service.app.models;


import jakarta.persistence.*;
import lombok.*;


import java.util.Objects;


@Data
@Entity(name = "bank_users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_account_name")
    private String accountName;

    @Column(name = "user_name")
    private String firstName;

    @Column(name = "user_surname")
    private String lastName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_invoice")
    private long invoice;


    @Column(name = "user_role", nullable = false)
    private String role;


    @Column(name = "user_balance")
    private Double balance;


    public BankUser(String accountName, String firstName, String lastName, String email, String password, Double balance) {
        this.accountName = accountName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankUser bankUser = (BankUser) o;
        return Objects.equals(id, bankUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
