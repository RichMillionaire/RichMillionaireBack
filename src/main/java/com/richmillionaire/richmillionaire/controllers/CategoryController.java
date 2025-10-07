package com.richmillionaire.richmillionaire.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.dto.CategoryDto;
import com.richmillionaire.richmillionaire.models.Category;
import com.richmillionaire.richmillionaire.services.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Récupérer toutes les catégories
    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    // Récupérer une catégorie par ID
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable UUID id) {
        return categoryService.getById(id);
    }

    // Ajouter une catégorie
    @PostMapping("")
    public void addCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
    }

    // Mettre à jour une catégorie
    @PutMapping("/{id}")
    public void updateCategory(@PathVariable UUID id, @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto, id);
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteById(id);
    }

    // // Récupérer les catégories d’un article
    // @GetMapping("/article/{articleId}")
    // public List<Category> getCategoriesByArticle(@PathVariable UUID articleId) {
    //     return categoryService.findByArticle(articleId);
    // }
}
