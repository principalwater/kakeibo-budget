package com.kakeibo.service;

import com.kakeibo.model.Transaction;
import com.kakeibo.repository.CategoryRepository;
import com.kakeibo.repository.TransactionRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CategorizationServiceTest {

    @Test
    void testCategorizeTransaction() {
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        CategorizationService categorizationService = new CategorizationService(transactionRepository, categoryRepository);

        Transaction transaction = new Transaction();
        transaction.setDescription("Grocery shopping at Supermarket");

        categorizationService.categorizeTransaction(transaction);

        verify(transactionRepository, times(1)).save(transaction);
    }
}