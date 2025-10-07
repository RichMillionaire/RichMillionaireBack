package com.richmillionaire.richmillionaire.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private List<UUID> categoryIds; // id catégories 
}
