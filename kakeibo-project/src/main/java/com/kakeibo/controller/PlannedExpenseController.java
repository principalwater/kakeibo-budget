package com.kakeibo.controller;

import com.kakeibo.model.PlannedExpense;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planned-expenses")
public class PlannedExpenseController {

    private List<PlannedExpense> plannedExpenses = new ArrayList<>();

    @GetMapping
    public List<PlannedExpense> getAllPlannedExpenses() {
        // Return all planned expenses
        return plannedExpenses;
    }

    @PostMapping
    public String addPlannedExpense(@RequestBody PlannedExpense plannedExpense) {
        // Add a new planned expense
        plannedExpenses.add(plannedExpense);
        return "Planned expense added: " + plannedExpense.getDescription();
    }

    @DeleteMapping("/{id}")
    public String deletePlannedExpense(@PathVariable int id) {
        // Delete a planned expense by ID
        plannedExpenses.removeIf(p -> p.getId() == id);
        return "Planned expense removed with ID: " + id;
    }
}