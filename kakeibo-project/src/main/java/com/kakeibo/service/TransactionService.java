package com.kakeibo.service;

import com.kakeibo.model.Transaction;
import com.kakeibo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsByCategoryId(Long categoryId) {
        return transactionRepository.findByCategoryId(categoryId);
    }

    public List<Transaction> getTransactionsByIncomeSourceId(Long incomeSourceId) {
        return transactionRepository.findByIncomeSourceId(incomeSourceId);
    }
}