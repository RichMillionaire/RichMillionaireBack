package com.richmillionaire.richmillionaire.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richmillionaire.richmillionaire.models.Article;

@Repository
public interface ArticleDao extends JpaRepository<Article, java.util.UUID> {
    List<Article> findByCategories_Id(java.util.UUID categoryId);
}
