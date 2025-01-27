package com.kakeibo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeSourceController {

    private List<String> incomeSources = new ArrayList<>();

    @GetMapping
    public List<String> getAllIncomeSources() {
        // Return all income sources
        return incomeSources;
    }

    @PostMapping
    public String addIncomeSource(@RequestBody String source) {
        // Add a new income source
        incomeSources.add(source);
        return "Income source added: " + source;
    }

    @DeleteMapping("/{source}")
    public String deleteIncomeSource(@PathVariable String source) {
        // Delete an income source
        incomeSources.remove(source);
        return "Income source removed: " + source;
    }
}