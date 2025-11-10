package com.richmillionaire.richmillionaire.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richmillionaire.richmillionaire.models.Article;

@Repository
public interface ArticleDao extends JpaRepository<Article, UUID> {

    List<Article> findByCategories_Id(UUID categoryId);

    // Rechercher par nom (contient, insensible à la casse)
    List<Article> findByNameContainingIgnoreCase(String keyword);

    // Rechercher par catégorie via le nom
    List<Article> findByCategories_Name(String categoryName);

    // Combiner nom + catégorie
    List<Article> findByNameContainingIgnoreCaseAndCategories_Name(String keyword, String categoryName);

    // Trier par prix croissant
    List<Article> findAllByOrderByPriceAsc();

    // Trier par prix décroissant
    List<Article> findAllByOrderByPriceDesc();

    // Pagination par catégorie (ex: pour éviter de ramener 1000 articles)
    List<Article> findByCategories_Id(UUID categoryId, Pageable pageable);

    Page<Article> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // Pagination par catégorie
    Page<Article> findByCategories_Name(String categoryName, Pageable pageable);

    // Pagination par keyword + catégorie
    Page<Article> findByNameContainingIgnoreCaseAndCategories_Name(String keyword, String categoryName, Pageable pageable);

    
}

