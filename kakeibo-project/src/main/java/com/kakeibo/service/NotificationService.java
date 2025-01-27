package com.kakeibo.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyLowBalance(double balance) {
        if (balance < 100) {
            System.out.println("Warning: Your balance is low: $" + balance);
        }
    }

    public void notifyCategoryOverspend(String category, double amountSpent, double limit) {
        if (amountSpent > limit) {
            System.out.println("Alert: You have overspent in the " + category + " category!");
        }
    }
}