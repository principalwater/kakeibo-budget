package com.kakeibo.model;

import com.kakeibo.utils.PaymentKind;

import java.time.LocalDate;

public class PaymentModel {

    private LocalDate date;
    private String accountName;
    private String description;
    private double amount;
    private PaymentKind paymentKind;
    private String currency;
    private String statementReference;

    public PaymentModel(
            LocalDate date,
            String accountName,
            String description,
            double amount,
            PaymentKind paymentKind,
            String currency,
            String statementReference
    ) {
        this.date = date;
        this.accountName = accountName;
        this.description = description;
        this.amount = amount;
        this.paymentKind = paymentKind;
        this.currency = currency;
        this.statementReference = statementReference;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentKind getPaymentKind() {
        return paymentKind;
    }

    public void setPaymentKind(PaymentKind paymentKind) {
        this.paymentKind = paymentKind;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatementReference() {
        return statementReference;
    }

    public void setStatementReference(String statementReference) {
        this.statementReference = statementReference;
    }
}