package com.kakeibo.service;

import com.kakeibo.model.Transaction;
import com.kakeibo.repository.CategoryRepository;
import com.kakeibo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategorizationService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public CategorizationService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public void categorizeTransaction(Transaction transaction) {
        Map<String, String> categoryKeywords = Map.of(
                "Groceries", "groceries,supermarket,market",
                "Utilities", "electricity,water,bill",
                "Entertainment", "movies,netflix,concert"
        );

        for (Map.Entry<String, String> entry : categoryKeywords.entrySet()) {
            String category = entry.getKey();
            String[] keywords = entry.getValue().split(",");
            for (String keyword : keywords) {
                if (transaction.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    transaction.setCategory(categoryRepository.findByName(category));
                    transactionRepository.save(transaction);
                    return;
                }
            }
        }

        // If no match found, assign to "Uncategorized"
        transaction.setCategory(categoryRepository.findByName("Uncategorized"));
        transactionRepository.save(transaction);
    }
}