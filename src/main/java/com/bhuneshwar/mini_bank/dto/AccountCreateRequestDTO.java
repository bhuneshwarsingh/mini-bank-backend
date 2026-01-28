package com.bhuneshwar.mini_bank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AccountCreateRequestDTO {

    @NotBlank(message = "Account type is required (SAVINGS/CURRENT)")
    private String accountType;

    @Min(value = 0, message = "Initial balance must be >= 0")
    private double initialBalance;

    public AccountCreateRequestDTO() {}

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }
}