package com.bhuneshwar.mini_bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String txnId;

    @Column(nullable = false)
    private String type; // DEPOSIT / WITHDRAW / TRANSFER

    @Column(nullable = false)
    private double amount;

    private String debitAccount;
    private String creditAccount;

    private String description;

    @Column(nullable = false)
    private double balanceAfter;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String txnId, String type, double amount,
                       String debitAccount, String creditAccount,
                       String description, double balanceAfter) {
        this.txnId = txnId;
        this.type = type;
        this.amount = amount;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.description = description;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTxnId() { return txnId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDebitAccount() { return debitAccount; }
    public String getCreditAccount() { return creditAccount; }
    public String getDescription() { return description; }
    public double getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

