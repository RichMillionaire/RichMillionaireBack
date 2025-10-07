package com.richmillionaire.richmillionaire.dto;

import java.io.IOException;
import java.util.UUID;

import com.richmillionaire.richmillionaire.models.Category;

public class CategoryMapper {

    public static Category fromDto(CategoryDto dto, UUID id) throws IOException {
        Category category = new Category();

        if (id != null) {
            category.setId(id);
        }

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
