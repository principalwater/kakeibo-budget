package com.kakeibo.service;

import com.kakeibo.model.Transaction;
import com.kakeibo.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TransactionServiceTest {

    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final TransactionService transactionService = new TransactionService(transactionRepository);

    @Test
    void testGetAllTransactions() {
        // Mock repository behavior
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setDescription("Groceries");
        transaction.setAmount(50.0);
        transaction.setTransactionDate(LocalDateTime.now());
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        // Call the service method
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Verify behavior
        assertThat(transactions).hasSize(1);
        assertThat(transactions.get(0).getDescription()).isEqualTo("Groceries");
        verify(transactionRepository, times(1)).findAll();
    }
}