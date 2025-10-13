package com.richmillionaire.richmillionaire.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richmillionaire.richmillionaire.dao.ArticleDao;
import com.richmillionaire.richmillionaire.dto.ArticleDto;
import com.richmillionaire.richmillionaire.dto.ArticleMapper;
import com.richmillionaire.richmillionaire.models.Article;

@Service
public class ArticleService {

    private final ArticleDao articleDao;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public List<Article> findAll() {
        Iterable<Article> it = articleDao.findAll();
        System.out.println(it);
        List<Article> articles = new ArrayList<>();
        it.forEach(articles::add);
        return articles;
    }

    public Article getById(UUID id) {
        return articleDao.findById(id).orElseThrow(() ->
                new NoSuchElementException("Article not found with id: " + id));
    }

    @Transactional
    public void deleteById(UUID id) {
        articleDao.deleteById(id);
    }

    @Transactional
    public void addArticle(ArticleDto articleDto) {
        Article article;
        try {
            article = ArticleMapper.fromDto(articleDto, null);
        } catch (IOException e) {
            throw new RuntimeException("Error while mapping Article DTO", e);
        }
        articleDao.save(article);
    }

    @Transactional
    public void updateArticle(ArticleDto articleDto, UUID id) {
        articleDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Article doesn't exist"));
        Article article;
        try {
            article = ArticleMapper.fromDto(articleDto, id);
        } catch (IOException e) {
            throw new RuntimeException("Error while mapping Article DTO", e);
        }
        articleDao.save(article);
    }

    public List<Article> findByCategoryId(UUID categoryId) {
        return articleDao.findByCategories_Id(categoryId);
    }
}
