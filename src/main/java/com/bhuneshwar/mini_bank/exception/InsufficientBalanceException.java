package com.bhuneshwar.mini_bank.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message)
    {
        super(message);
    }
}
