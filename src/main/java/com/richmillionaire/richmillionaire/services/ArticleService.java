package com.richmillionaire.richmillionaire.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.richmillionaire.richmillionaire.dao.ArticleDao;
import com.richmillionaire.richmillionaire.dto.ArticleDto;
import com.richmillionaire.richmillionaire.dto.ArticleMapper;
import com.richmillionaire.richmillionaire.models.Article;

@Service
public class ArticleService {

    private final ArticleDao articleDao;
    private static final String UPLOAD_DIR = "upload/articles/";

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public Page<Article> findAll(Pageable pageable) {
        return articleDao.findAll(pageable);
    }

    public Article getById(UUID id) {
        return articleDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Article not found with id: " + id));
    }

    @Transactional
    public Article deleteById(UUID id) {
        Article article = articleDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Article not found"));
        articleDao.deleteById(id);
        return article;
    }

    @Transactional
    public Article addArticle(ArticleDto articleDto) {
        Article article;
        try {
            article = ArticleMapper.fromDto(articleDto, null);
        } catch (IOException e) {
            throw new RuntimeException("Error while mapping Article DTO", e);
        }
        articleDao.save(article);
        return article;
    }

    @Transactional
    public Article updateArticle(ArticleDto dto, UUID id) {
        Article article = articleDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Article not found"));

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            article.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            article.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            article.setPrice(dto.getPrice());
        }
        if (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty()) {
            article.setPhotoUrl(dto.getImageUrl());
        }

        return articleDao.save(article);
    }

    public List<Article> findByCategoryId(UUID categoryId) {
        return articleDao.findByCategories_Id(categoryId);
    }

    public List<Article> searchArticles(String keyword, String categoryName, String sort) {
        if ("asc".equalsIgnoreCase(sort)) {
            return articleDao.findByCategories_Name(categoryName)
                            .stream().sorted(Comparator.comparing(Article::getPrice)).toList();
        } else if ("desc".equalsIgnoreCase(sort)) {
            return articleDao.findByCategories_Name(categoryName)
                            .stream().sorted(Comparator.comparing(Article::getPrice).reversed()).toList();
        } else if (keyword != null && !keyword.isEmpty() && categoryName != null) {
            return articleDao.findByNameContainingIgnoreCaseAndCategories_Name(keyword, categoryName);
        } else if (keyword != null && !keyword.isEmpty()) {
            return articleDao.findByNameContainingIgnoreCase(keyword);
        } else if (categoryName != null) {
            return articleDao.findByCategories_Name(categoryName);
        } else {
            return articleDao.findAll();
        }
    }

    // ========== MÉTHODES AVEC GESTION DES IMAGES ==========

    @Transactional
    public Article addArticleWithImage(ArticleDto articleDto, MultipartFile image) throws IOException {
        Article article;
        try {
            article = ArticleMapper.fromDto(articleDto, null);
        } catch (IOException e) {
            throw new RuntimeException("Error while mapping Article DTO", e);
        }
        
        // Sauvegarder pour obtenir l'ID
        article = articleDao.save(article);
        
        // Gérer l'upload de l'image si présente
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(article.getId(), image);
            article.setPhotoUrl(imagePath);
            article = articleDao.save(article);
        }
        
        return article;
    }

    @Transactional
    public Article updateArticleWithImage(ArticleDto dto, UUID id, MultipartFile image) throws IOException {
        Article article = articleDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Article not found"));

        // Mettre à jour les champs de base
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            article.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            article.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            article.setPrice(dto.getPrice());
        }

        // Gérer la nouvelle image si fournie
        if (image != null && !image.isEmpty()) {
            // Supprimer l'ancienne image
            deleteImageFile(id);
            
            // Sauvegarder la nouvelle image
            String imagePath = saveImage(id, image);
            article.setPhotoUrl(imagePath);
        } else if (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty()) {
            article.setPhotoUrl(dto.getImageUrl());
        }

        return articleDao.save(article);
    }

    @Transactional
    public Article deleteArticleWithImages(UUID id) {
        Article article = articleDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Article not found"));
        
        // Supprimer le fichier image
        deleteImageFile(id);
        
        // Supprimer de la base de données
        articleDao.deleteById(id);
        
        return article;
    }

    // ========== MÉTHODES UTILITAIRES PRIVÉES ==========

    private String saveImage(UUID articleId, MultipartFile file) throws IOException {
        // Créer le dossier upload/articles si nécessaire
        Path uploadDir = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadDir);

        // Récupérer l'extension du fichier
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        // Nom du fichier : {uuid}.{extension}
        String filename = articleId.toString() + extension;

        // Sauvegarder le fichier
        Path targetLocation = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Retourner le chemin relatif
        return UPLOAD_DIR + filename;
    }

    private void deleteImageFile(UUID articleId) {
        try {
            Path uploadDir = Paths.get(UPLOAD_DIR);
            
            // Chercher tous les fichiers commençant par l'ID de l'article
            if (Files.exists(uploadDir)) {
                Files.list(uploadDir)
                    .filter(path -> path.getFileName().toString().startsWith(articleId.toString()))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("Image supprimée : " + path);
                        } catch (IOException e) {
                            System.err.println("Erreur lors de la suppression du fichier: " + path);
                        }
                    });
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la recherche des fichiers pour l'article: " + articleId);
        }
    }
}