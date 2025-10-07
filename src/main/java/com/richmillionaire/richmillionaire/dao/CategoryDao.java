package com.richmillionaire.richmillionaire.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richmillionaire.richmillionaire.models.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, java.util.UUID> {
    List<Category> findByArticles_Id(java.util.UUID articleId);
}
