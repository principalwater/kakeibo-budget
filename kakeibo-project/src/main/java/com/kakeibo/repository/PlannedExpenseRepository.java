package com.kakeibo.repository;

import com.kakeibo.model.PlannedExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedExpenseRepository extends JpaRepository<PlannedExpense, Long> {
    // Custom query to find planned expenses by due date
    List<PlannedExpense> findByDueDateBefore(java.time.LocalDate date);
}