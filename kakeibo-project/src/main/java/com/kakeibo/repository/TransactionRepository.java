package com.kakeibo.repository;

import com.kakeibo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Custom query to find transactions by category ID
    List<Transaction> findByCategoryId(Long categoryId);

    // Custom query to find transactions by income source ID
    List<Transaction> findByIncomeSourceId(Long incomeSourceId);
}