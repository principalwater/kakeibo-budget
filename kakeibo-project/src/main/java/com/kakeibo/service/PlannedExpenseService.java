package com.kakeibo.service;

import com.kakeibo.model.PlannedExpense;
import com.kakeibo.repository.PlannedExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlannedExpenseService {

    private final PlannedExpenseRepository plannedExpenseRepository;

    public PlannedExpenseService(PlannedExpenseRepository plannedExpenseRepository) {
        this.plannedExpenseRepository = plannedExpenseRepository;
    }

    public List<PlannedExpense> getAllPlannedExpenses() {
        return plannedExpenseRepository.findAll();
    }

    public Optional<PlannedExpense> getPlannedExpenseById(Long id) {
        return plannedExpenseRepository.findById(id);
    }

    public PlannedExpense savePlannedExpense(PlannedExpense plannedExpense) {
        return plannedExpenseRepository.save(plannedExpense);
    }

    public void deletePlannedExpense(Long id) {
        plannedExpenseRepository.deleteById(id);
    }

    public List<PlannedExpense> getPlannedExpensesDueBefore(LocalDate date) {
        return plannedExpenseRepository.findByDueDateBefore(date);
    }
}