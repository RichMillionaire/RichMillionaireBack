package com.richmillionaire.richmillionaire.dto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.richmillionaire.richmillionaire.models.Article;
import com.richmillionaire.richmillionaire.models.Category;

public class ArticleMapper {

    public static Article fromDto(ArticleDto dto, UUID id) throws IOException {
        if (dto == null) throw new IOException("ArticleDto is null");

        Article article = new Article();
        if (id != null) {
            article.setId(id);
        }

        article.setName(dto.getName());
        article.setDescription(dto.getDescription());
        article.setPrice(dto.getPrice());
        article.setPhotoUrl(dto.getImageUrl()); 

        // Conversion des UUID de catégories vers des objets Category
        if (dto.getCategoryIds() != null) {
            Set<Category> categories = new HashSet<>();
            for (UUID categoryId : dto.getCategoryIds()) {
                Category category = new Category();
                category.setId(categoryId);
                categories.add(category);
            }
            article.setCategories(categories);
        }

        return article;
    }
}
