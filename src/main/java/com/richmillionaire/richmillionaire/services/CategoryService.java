package com.richmillionaire.richmillionaire.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richmillionaire.richmillionaire.dao.ArticleDao;
import com.richmillionaire.richmillionaire.dao.CategoryDao;
import com.richmillionaire.richmillionaire.dto.CategoryDto;
import com.richmillionaire.richmillionaire.dto.CategoryMapper;
import com.richmillionaire.richmillionaire.models.Article;
import com.richmillionaire.richmillionaire.models.Category;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ArticleDao articleDao;

    public CategoryService(CategoryDao categoryDao, ArticleDao articleDao) {
        this.categoryDao = categoryDao;
        this.articleDao = articleDao;
    }

    public List<Category> findAll() {
        Iterable<Category> it = categoryDao.findAll();
        List<Category> categories = new ArrayList<>();
        it.forEach(categories::add);
        return categories;
    }

    public Category getById(UUID id) {
        return categoryDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }

    @Transactional
    public void deleteById(UUID id) {
        categoryDao.deleteById(id);
    }

    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category category;
        try {
            category = CategoryMapper.fromDto(categoryDto, null);
        } catch (IOException e) {
            throw new RuntimeException("Error while mapping Category DTO", e);
        }
        categoryDao.save(category);
    }

    @Transactional
    public void putCategory(UUID id, CategoryDto categoryDto) {
        Category category = categoryDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
        if (categoryDto.getName() != null && !categoryDto.getName().isEmpty()) {
            category.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription() != null && !categoryDto.getDescription().isEmpty()) {
            category.setDescription(categoryDto.getDescription());
        }

        categoryDao.save(category);
    }

    public List<Category> findByArticleId(UUID articleId) {
        Article article = articleDao.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("Article not found with id: " + articleId));
        return new ArrayList<>(article.getCategories());
    }
}
