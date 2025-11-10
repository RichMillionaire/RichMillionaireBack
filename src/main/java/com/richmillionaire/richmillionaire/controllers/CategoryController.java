package com.richmillionaire.richmillionaire.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(ApiResponse.success("Liste des catégories récupérée ✅", categories));
    }

    // Récupérer une catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Catégorie récupérée ✅", category));
    }

    // Ajouter une catégorie
    @PostMapping("")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody CategoryDto categoryDto) {
        Category created = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(ApiResponse.success("Catégorie ajoutée ✅", created));
    }

    // Modifier une catégorie
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> patchCategory(@PathVariable UUID id, @RequestBody CategoryDto categoryDto) {
        Category updated = categoryService.putCategory(id, categoryDto);
        return ResponseEntity.ok(ApiResponse.success("Catégorie mise à jour ✅", updated));
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategory(@PathVariable UUID id) {
        Category deleted = categoryService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Catégorie supprimée ✅", deleted));
    }

    // Récupérer les catégories d’un article
    @GetMapping("/article/{articleId}")
    public ResponseEntity<ApiResponse<List<Category>>> getCategoriesByArticle(@PathVariable UUID articleId) {
        List<Category> categories = categoryService.findByArticleId(articleId);
        return ResponseEntity.ok(ApiResponse.success("Catégories de l'article récupérées ✅", categories));
    }
}
