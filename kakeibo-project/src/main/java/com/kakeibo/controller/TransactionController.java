package com.kakeibo.controller;

import com.kakeibo.model.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private List<Transaction> transactions = new ArrayList<>();

    @GetMapping
    public List<Transaction> getAllTransactions() {
        // Return all transactions
        return transactions;
    }

    @PostMapping
    public String addTransaction(@RequestBody Transaction transaction) {
        // Add a new transaction
        transactions.add(transaction);
        return "Transaction added: " + transaction.getDescription();
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable int id) {
        // Delete a transaction by ID
        transactions.removeIf(t -> t.getId() == id);
        return "Transaction removed with ID: " + id;
    }
}