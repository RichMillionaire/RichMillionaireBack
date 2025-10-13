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

import com.richmillionaire.richmillionaire.dto.ArticleDto;
import com.richmillionaire.richmillionaire.models.Article;
import com.richmillionaire.richmillionaire.services.ArticleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Récupérer tous les articles
    @GetMapping("")
    public List<Article> getAllArticles() {
        return articleService.findAll();
    }

    // Récupérer un article par ID
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable UUID id) {
        return articleService.getById(id);
    }

    // Ajouter un article
    @PostMapping("")
    public void addArticle(@RequestBody ArticleDto articleDto) {
        articleService.addArticle(articleDto);
    }

    // Mettre à jour un article
    @PutMapping("/{id}")
    public void updateArticle(@PathVariable UUID id, @RequestBody ArticleDto articleDto) {
        articleService.updateArticle(articleDto, id);
    }

    // Supprimer un article
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable UUID id) {
        articleService.deleteById(id);
    }

    // // Récupérer les articles d'une catégorie
    // @GetMapping("/category/{categoryId}")
    // public List<Article> getArticlesByCategory(@PathVariable UUID categoryId) {
    //     return articleService.findByCategory(categoryId);
    // }
}
