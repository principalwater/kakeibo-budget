package com.kakeibo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @GetMapping("/summary")
    public String getBudgetSummary() {
        // Placeholder logic for returning budget summary
        return "Budget Summary: Total Income: $5000, Total Expenses: $3000, Savings: $2000";
    }

    @GetMapping("/analysis")
    public String getBudgetAnalysis() {
        // Placeholder logic for returning budget analysis
        return "Budget Analysis: Expenses are within the allocated budget!";
    }
}