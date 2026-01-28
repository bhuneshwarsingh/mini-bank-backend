package com.bhuneshwar.mini_bank.dto;


public class AccountResponseDTO {

    private String accountNumber;
    private String accountType;
    private double balance;
    private String status;

    public AccountResponseDTO() {}

    public AccountResponseDTO(String accountNumber, String accountType, double balance, String status) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }
}
