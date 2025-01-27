package com.kakeibo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private List<String> categories = new ArrayList<>();

    @GetMapping
    public List<String> getAllCategories() {
        // Return a list of categories
        return categories;
    }

    @PostMapping
    public String addCategory(@RequestBody String category) {
        // Add a new category
        categories.add(category);
        return "Category added: " + category;
    }

    @DeleteMapping("/{category}")
    public String deleteCategory(@PathVariable String category) {
        // Delete a category
        categories.remove(category);
        return "Category removed: " + category;
    }
}