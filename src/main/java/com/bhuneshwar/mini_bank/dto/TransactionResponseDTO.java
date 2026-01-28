package com.bhuneshwar.mini_bank.dto;

import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
public class TransactionResponseDTO {

    private String txnId;
    private String type;
    private double amount;

    private String debitAccount;
    private String creditAccount;

    private String description;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public TransactionResponseDTO(){}

    public TransactionResponseDTO(String txnId,String type,double amount,
                                  String debitAccount,String creditAccount, String description,
                                  double balanceAfter,LocalDateTime timestamp)
    {
    this.txnId = txnId;
        this.type = type;
        this.amount = amount;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.description = description;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp;
}

        public String getTxnId() { return txnId; }
        public String getType() { return type; }
        public double getAmount() { return amount; }
        public String getDebitAccount() { return debitAccount; }
        public String getCreditAccount() { return creditAccount; }
        public String getDescription() { return description; }
        public double getBalanceAfter() { return balanceAfter; }
        public LocalDateTime getTimestamp() { return timestamp; }

}
