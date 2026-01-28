package com.bhuneshwar.mini_bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class WithdrawRequestDTO {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @Min(value = 1, message = "Withdraw amount must be greater than 0")
    private double amount;

    public WithdrawRequestDTO() {}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
