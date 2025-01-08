package com.aadhie.expensetracker.services;

import com.aadhie.expensetracker.models.Category;
import com.aadhie.expensetracker.repositories.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    public CategoryService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getAllCategories() {
        return this.categoriesRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = this.categoriesRepository.findById(id);
        return category.orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }
}
