package com.steve.bank.exception;

public class BankException extends RuntimeException{

    public BankException(String message, Throwable cause) {
        super(message, cause);
    }
}
