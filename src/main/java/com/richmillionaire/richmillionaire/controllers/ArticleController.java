package com.richmillionaire.richmillionaire.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richmillionaire.richmillionaire.dto.ArticleDto;
import com.richmillionaire.richmillionaire.models.Article;
import com.richmillionaire.richmillionaire.services.ArticleService;

@CrossOrigin
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ObjectMapper objectMapper;

    public ArticleController(ArticleService articleService, ObjectMapper objectMapper) {
        this.articleService = articleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<Article>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articlesPage = articleService.findAll(pageable);
        return ResponseEntity.ok(articlesPage);
    }

    @GetMapping("/search")
    public List<Article> searchArticles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sort) {
        return articleService.searchArticles(keyword, category, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable UUID id) {
        Article article = articleService.getById(id);
        return ResponseEntity.ok(article);
    }

    // Version simple sans fichier (comme CategoryController)
    @PostMapping("")
    public ResponseEntity<Article> addArticleSimple(@RequestBody ArticleDto articleDto) {
        Article article = articleService.addArticle(articleDto);
        return ResponseEntity.ok(article);
    }

    // Version avec upload de fichier
    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Article> addArticleWithImage(
            @RequestPart("article") String articleJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ArticleDto articleDto = objectMapper.readValue(articleJson, ArticleDto.class);
        Article article = articleService.addArticleWithImage(articleDto, image);
        return ResponseEntity.ok(article);
    }

    // Version simple pour update
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticleSimple(
            @PathVariable UUID id, 
            @RequestBody ArticleDto articleDto
    ) {
        Article updated = articleService.updateArticle(articleDto, id);
        return ResponseEntity.ok(updated);
    }

    // Version update avec image
    @PutMapping(value = "/{id}/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Article> updateArticleWithImage(
            @PathVariable UUID id,
            @RequestPart("article") String articleJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ArticleDto articleDto = objectMapper.readValue(articleJson, ArticleDto.class);
        Article updated = articleService.updateArticleWithImage(articleDto, id, image);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable UUID id) {
        Article deleted = articleService.deleteArticleWithImages(id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Article>> getArticlesByCategory(@PathVariable UUID categoryId) {
        List<Article> articles = articleService.findByCategoryId(categoryId);
        return ResponseEntity.ok(articles);
    }
}