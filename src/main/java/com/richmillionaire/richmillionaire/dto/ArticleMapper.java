package com.richmillionaire.richmillionaire.dto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.richmillionaire.richmillionaire.models.Article;
import com.richmillionaire.richmillionaire.models.Category;

public class ArticleMapper {

    public static Article fromDto(ArticleDto dto, UUID id) throws IOException {
        Article article = new Article();

        if (id != null) {
            article.setId(id);
        }

        article.setName(dto.getName());
        article.setDescription(dto.getDescription());
        article.setPrice(dto.getPrice());
        article.setPhotoUrl(dto.getImageUrl());

        // On set les catégories uniquement par leurs IDs (pas les objets complets ici)
        if (dto.getCategoryIds() != null) {
            Set<Category> categories = dto.getCategoryIds().stream()
                    .map(categoryId -> {
                        Category c = new Category();
                        c.setId(categoryId);
                        return c;
                    })
                    .collect(Collectors.toSet());
            article.setCategories(categories);
        } else {
            article.setCategories(new HashSet<>());
        }

        return article;
    }
}
