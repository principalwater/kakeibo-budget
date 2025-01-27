package com.kakeibo.repository;

import com.kakeibo.model.BudgetGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetGoalRepository extends JpaRepository<BudgetGoal, Long> {
    // Custom query to find goals by name
    BudgetGoal findByGoalName(String goalName);
}