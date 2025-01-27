package com.kakeibo.model;

public class MoneyStateModel {

    private String name;
    private double amount;
    private String currency;

    public MoneyStateModel(String name, double amount, String currency) {
        this.name = name;
        this.amount = amount;
        this.currency = currency;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}