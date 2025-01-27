package com.kakeibo;

import com.kakeibo.model.Transaction;
import com.kakeibo.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testSaveAndFindTransaction() {
        // Save a new transaction
        Transaction transaction = new Transaction();
        transaction.setDescription("Groceries");
        transaction.setAmount(50.0);
        transaction.setTransactionDate(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Verify the saved transaction
        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getDescription()).isEqualTo("Groceries");

        // Find all transactions
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions).hasSize(1);
    }
}