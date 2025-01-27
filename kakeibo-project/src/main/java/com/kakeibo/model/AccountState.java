package com.kakeibo.model;

public class AccountState {
    private String accountName;
    private double balance;
    private String currency;

    public AccountState(String accountName, double balance, String currency) {
        this.accountName = accountName;
        this.balance = balance;
        this.currency = currency;
    }

    // Getters and setters...
}