package com.kakeibo.service;

import com.kakeibo.model.BudgetGoal;
import com.kakeibo.repository.BudgetGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetGoalService {

    private final BudgetGoalRepository budgetGoalRepository;

    public BudgetGoalService(BudgetGoalRepository budgetGoalRepository) {
        this.budgetGoalRepository = budgetGoalRepository;
    }

    public List<BudgetGoal> getAllBudgetGoals() {
        return budgetGoalRepository.findAll();
    }

    public Optional<BudgetGoal> getBudgetGoalById(Long id) {
        return budgetGoalRepository.findById(id);
    }

    public BudgetGoal saveBudgetGoal(BudgetGoal budgetGoal) {
        return budgetGoalRepository.save(budgetGoal);
    }

    public void deleteBudgetGoal(Long id) {
        budgetGoalRepository.deleteById(id);
    }

    public Optional<BudgetGoal> getBudgetGoalByName(String name) {
        return Optional.ofNullable(budgetGoalRepository.findByGoalName(name));
    }
}